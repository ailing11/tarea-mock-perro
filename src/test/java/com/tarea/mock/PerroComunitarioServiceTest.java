package com.tarea.mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.tarea.mock.entidades.Perro;
import com.tarea.mock.excepciones.PerroNoEncontradoException;
import com.tarea.mock.repositorios.PerroRepository;
import com.tarea.mock.servicios.PerroComunitarioService;

public class PerroComunitarioServiceTest {

    PerroRepository mockRepository;
    PerroComunitarioService service;

    @BeforeEach
    public void inicializarPrueba() {
        // Mock del repositorio
        mockRepository = Mockito.mock(PerroRepository.class);
        // Servicio a probar
        service = new PerroComunitarioService(mockRepository);
    }

    @Test
    public void deberiaDevolverPerroCuandoElPerroExiste() {
        // Simular que el repositorio devuelve un perro con nombre "Fido" y edad 4
        Perro perroEsperado = new Perro("Fido", 4);
        when(mockRepository.buscarPorNombre("Fido")).thenReturn(perroEsperado);

        // Llamar al servicio con un nombre válido
        Perro resultado = service.obtenerPerroPorNombre("Fido");

        // Verificación
        assertEquals("Fido", resultado.getNombre());
        assertEquals(4, resultado.getEdad());
    }

    @Test
    public void deberiaLanzarPerroNoEncontradoExceptioCuandoElPerroNoExiste() {
        // Simular que el repositorio no encuentra un perro con el nombre "Rex"
        when(mockRepository.buscarPorNombre("Rex")).thenReturn(null);

        // Verificacion PerroNoEncontradoException
        assertThrows(PerroNoEncontradoException.class, () -> service.obtenerPerroPorNombre("Rex"));
    }

    @Test
    public void deberiaLanzarIllegalArgumentExceptionCuandoElNombreEsNull() {
        // Verificación
        assertThrows(IllegalArgumentException.class, () -> service.obtenerPerroPorNombre(null));
    }

    @Test
    public void deberiaLanzarIllegalArgumentExceptionCuandoElNombreEsVacio() {
        // Verificación 
        assertThrows(IllegalArgumentException.class, () -> service.obtenerPerroPorNombre(""));
    }

    @Test
    public void deberiaConsultarRepositorioUnaSolaVezCuandoElPerroExiste() {
        // Simulación devuelve un perro con nombre "Fido"
        Perro perroEsperado = new Perro("Fido", 4);
        when(mockRepository.buscarPorNombre("Fido")).thenReturn(perroEsperado);

        service.obtenerPerroPorNombre("Fido");

        // Verificación
        verify(mockRepository, times(1)).buscarPorNombre("Fido");
    }
}

