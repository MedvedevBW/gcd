package com.bwsw.test.gcd.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import com.bwsw.test.gcd.configuration.AppConfig;
import com.bwsw.test.gcd.entities.GcdArgumentsBase;
import com.bwsw.test.gcd.entities.GcdCalculationRequest;
import com.bwsw.test.gcd.entities.GcdCalculationStatus;
import com.bwsw.test.gcd.entities.GcdResult;
import com.bwsw.test.gcd.rabbitmq.Producer;
import com.bwsw.test.gcd.repositories.GcdArgumentsRepository;
import com.bwsw.test.gcd.repositories.GcdResultRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.*;

@SpringBootTest(classes = GcdService.class)
public class GcdServiceTests extends AbstractTestNGSpringContextTests {
    @MockBean
    private RabbitTemplate rabbitTemplate = mock(RabbitTemplate.class);
    @MockBean
    private AppConfig config = mock(AppConfig.class);
    @MockBean
    private Producer<GcdCalculationRequest> producer = mock(Producer.class);
    @MockBean
    private GcdArgumentsRepository gcdArgumentsRepositoryMock = mock(GcdArgumentsRepository.class);
    @MockBean
    private GcdResultRepository gcdResultRepositoryMock = mock(GcdResultRepository.class);

    private GcdService gcdService = new GcdService(
            rabbitTemplate,
            config,
            producer,
            gcdArgumentsRepositoryMock,
            gcdResultRepositoryMock
    );

    @BeforeMethod
    void resetMocks() {
        reset(producer, gcdArgumentsRepositoryMock, gcdResultRepositoryMock);
    }

    @Test
    public void get_will_return_result_from_db() {
        Long resultId = 1L;
        Long result = 10L;
        GcdResult expectedResult = new GcdResult(resultId, result);

        when(gcdResultRepositoryMock.findById(resultId)).thenReturn(Optional.of(expectedResult));

        assertEquals(gcdService.get(resultId), expectedResult);
    }

    @Test
    public void get_will_return_result_with_error() {
        Long resultId = 1L;
        String errorMessage = "Entity with specified id does not exist";
        GcdResult expectedResult = new GcdResult(resultId, errorMessage);

        when(gcdResultRepositoryMock.findById(resultId)).thenReturn(Optional.empty());
        GcdResult actualResult = gcdService.get(resultId);

        assertEquals(actualResult.getId(), expectedResult.getId());
        assertEquals(actualResult.getStatus(), expectedResult.getStatus());
        assertEquals(actualResult.getError(), expectedResult.getError());
        assertNull(actualResult.getResult());
    }

    @Test
    public void calculate_will_save_new_arguments_in_base_then_send_it_and_return_id_of_it() {
        Long resultId = 1L;
        Long first = 10L;
        Long second = 12L;
        GcdArgumentsBase argumentsBase = new GcdArgumentsBase(first, second);
        argumentsBase.setId(resultId);

        when(gcdArgumentsRepositoryMock.findByFirstAndSecond(first, second)).thenReturn(new LinkedList<>());
        when(gcdArgumentsRepositoryMock.save(any())).thenReturn(argumentsBase);

        assertEquals(gcdService.calculate(first, second), resultId);

        verify(producer, times(1)).sendMessage(any(), any(), any(), any());
        verify(gcdResultRepositoryMock, times(1)).save(any());
    }

    @Test
    public void calculate_will_retrieve_arguments_from_base_then_resend_it_and_return_id_of_it() {
        Long resultId = 1L;
        Long first = 10L;
        Long second = 12L;

        GcdArgumentsBase argumentsBase = new GcdArgumentsBase(first, second);
        argumentsBase.setId(resultId);

        LinkedList<GcdArgumentsBase> listWithArguments = new LinkedList<>();
        listWithArguments.add(argumentsBase);

        GcdResult expectedResult = new GcdResult(resultId, GcdCalculationStatus.error);

        when(gcdArgumentsRepositoryMock.findByFirstAndSecond(first, second)).thenReturn(listWithArguments);
        when(gcdResultRepositoryMock.findById(resultId)).thenReturn(Optional.of(expectedResult));

        assertEquals(gcdService.calculate(first, second), resultId);

        verify(producer, times(1)).sendMessage(any(), any(), any(), any());
        verify(gcdResultRepositoryMock, never()).save(any());
    }

    @Test
    public void calculate_will_retrieve_arguments_from_base_then_return_id_of_it() {
        Long resultId = 1L;
        Long first = 10L;
        Long second = 12L;

        GcdArgumentsBase argumentsBase = new GcdArgumentsBase(first, second);
        argumentsBase.setId(resultId);

        LinkedList<GcdArgumentsBase> listWithArguments = new LinkedList<>();
        listWithArguments.add(argumentsBase);

        GcdResult expectedResult = new GcdResult(resultId, GcdCalculationStatus.notCompleted);

        when(gcdArgumentsRepositoryMock.findByFirstAndSecond(first, second)).thenReturn(listWithArguments);
        when(gcdResultRepositoryMock.findById(resultId)).thenReturn(Optional.of(expectedResult));

        assertEquals(gcdService.calculate(first, second), resultId);

        verify(producer, never()).sendMessage(any(), any(), any(), any());
        verify(gcdResultRepositoryMock, never()).save(any());
    }
}
