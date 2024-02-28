package com.sorveteria.alimentos.service;

import com.sorveteria.alimentos.dto.AlimentoDto;
import com.sorveteria.alimentos.model.Alimento;
import com.sorveteria.alimentos.model.Tipo;
import com.sorveteria.alimentos.repository.AlimentoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlimentoServiceTest {

    @Mock
    private AlimentoRepository alimentoRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private AlimentoService alimentoService;

    @Test
    void obterTodos_ShouldReturnListOfAlimentoDto() {
        // Test setup
        Alimento alimento1 = new Alimento(1L, Tipo.SORVETE, BigDecimal.TEN);
        Alimento alimento2 = new Alimento(2L, Tipo.MILKSHAKE, BigDecimal.valueOf(12));
        List<Alimento> alimentos = List.of(alimento1, alimento2);
        Page<Alimento> alimentosPage = new PageImpl<>(alimentos);
        Pageable pageable = Pageable.ofSize(10);

        AlimentoDto dto1 = new AlimentoDto();
        AlimentoDto dto2 = new AlimentoDto();

        // Mocking behavior
        when(alimentoRepository.findAll(pageable)).thenReturn(alimentosPage);
        when(modelMapper.map(alimento1, AlimentoDto.class)).thenReturn(dto1);
        when(modelMapper.map(alimento2, AlimentoDto.class)).thenReturn(dto2);

        // Exercise - Call the method under test
        Page<AlimentoDto> result = alimentoService.obterTodos(pageable);

        // Assertions
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getContent()).containsExactlyInAnyOrder(dto1, dto2);
    }

    @Test
    void obterPorId_ShouldReturnAlimentoDto() {
        // Setup
        Long alimentoId = 1L;
        Alimento alimento = new Alimento(alimentoId, Tipo.SORVETE, BigDecimal.TEN);
        AlimentoDto dto = new AlimentoDto();

        // Mocks e comportamento
        when(alimentoRepository.findById(alimentoId)).thenReturn(Optional.of(alimento));
        when(modelMapper.map(alimento, AlimentoDto.class)).thenReturn(dto);

        // Execução e Asserções
        AlimentoDto result = alimentoService.obterPorId(alimentoId);

        assertThat(result).isNotNull();
        // Verifique se os campos mapeados do Alimento estão corretos
        // ...
    }

    @Test
    void obterPorId_ShouldThrowExceptionForNonExistingId() {
        // Setup
        Long nonExistentId = 99L;

        // Mock e comportamento
        when(alimentoRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // Execução e Asserções
        assertThrows(EntityNotFoundException.class, () -> alimentoService.obterPorId(nonExistentId));
    }

    @Test
    void criaAlimento_ShouldCreateAndReturnAlimentoDto() {
        // Setup
        Alimento novoAlimento = new Alimento(null, Tipo.MILKSHAKE, BigDecimal.valueOf(12.5));
        Alimento alimentoSalvo = new Alimento(1L, Tipo.MILKSHAKE, BigDecimal.valueOf(12.5));
        AlimentoDto dto = new AlimentoDto();

        // Mocks e comportamento
        when(alimentoRepository.save(novoAlimento)).thenReturn(alimentoSalvo);
        when(modelMapper.map(alimentoSalvo, AlimentoDto.class)).thenReturn(dto);

        // Crie um AlimentoDto com os dados do Alimento
        dto.setTipo(novoAlimento.getTipo());
        dto.setValor(novoAlimento.getValor());

        // Execução e Asserções
        AlimentoDto result = alimentoService.criaAlimento(dto);

        assertThat(result).isNotNull();
        // Verifique se os campos mapeados do Alimento estão corretos
        // ...
    }


    @Test
    void criaAlimento_ShouldThrowExceptionForNegativePrice() {
        // Setup
        // Creation using default constructor
        AlimentoDto novoAlimento = new AlimentoDto();

        // Setting values
        novoAlimento.setId(null);
        novoAlimento.setTipo(Tipo.SORVETE);
        novoAlimento.setValor(BigDecimal.valueOf(-1.5));


        // Execução e Asserções
        assertThrows(IllegalArgumentException.class, () -> alimentoService.criaAlimento(novoAlimento));
    }


    @Test
    void atualizaAlimento_ShouldUpdateAndReturnAlimentoDto() {
        // Setup
        Long alimentoId = 1L;
        Alimento alimentoAtualizado = new Alimento(alimentoId, Tipo.MILKSHAKE, BigDecimal.valueOf(12.5));
        Alimento alimentoSalvo = new Alimento(alimentoId, Tipo.MILKSHAKE, BigDecimal.valueOf(12.5));
        AlimentoDto dto = new AlimentoDto();

        // Mocks e comportamento
        when(alimentoRepository.existsById(alimentoId)).thenReturn(true);
        when(alimentoRepository.save(alimentoAtualizado)).thenReturn(alimentoSalvo);
        when(modelMapper.map(alimentoSalvo, AlimentoDto.class)).thenReturn(dto);

        // Crie um AlimentoDto com os dados atualizados
        dto.setTipo(alimentoAtualizado.getTipo());
        dto.setValor(alimentoAtualizado.getValor());
        // ...

        // Execução e Asserções
        AlimentoDto result = alimentoService.atualizaAlimento(alimentoId, dto);

        assertThat(result).isNotNull();
        // Verifique se os campos mapeados do Alimento estão corretos
        // ...
    }


    @Test
    void atualizaAlimento_ShouldThrowExceptionForNonExistingId() {
        // Setup
        Long alimentoId = 99L;
        Alimento alimentoAtualizado = new Alimento(alimentoId, Tipo.SORVETE, BigDecimal.valueOf(-1.5));
        AlimentoDto alimentoDto = new AlimentoDto(); // Create an empty DTO

        // Map the updated values (if needed)
        alimentoDto.setTipo(alimentoAtualizado.getTipo());
        alimentoDto.setValor(alimentoAtualizado.getValor());
        // ... and any other fields

        // Mocking behavior
        when(alimentoRepository.existsById(alimentoId)).thenReturn(false);

        // Execution and Assertions
        assertThrows(EntityNotFoundException.class, () -> alimentoService.atualizaAlimento(alimentoId, alimentoDto));
    }

    @Test
    void excluirAlimento_ShouldDeleteExistingAlimento() {
        // Setup
        Long alimentoId = 1L;

        // Mocks e comportamento
        when(alimentoRepository.existsById(alimentoId)).thenReturn(true);
        doNothing().when(alimentoRepository).deleteById(alimentoId);

        // Execução
        alimentoService.excluirAlimento(alimentoId);

        // Verificação
        verify(alimentoRepository).existsById(alimentoId);
        verify(alimentoRepository).deleteById(alimentoId);
    }

    @Test
    void excluirAlimento_ShouldNotDeleteNonExistingAlimento() {
        // Setup
        Long alimentoId = 99L;

        // Mocks e comportamento
        when(alimentoRepository.existsById(alimentoId)).thenReturn(false);

        // Execução e Asserções
        assertThrows(EntityNotFoundException.class, () -> alimentoService.excluirAlimento(alimentoId));

        // Verificação
        verify(alimentoRepository).existsById(alimentoId);
        verify(alimentoRepository, never()).deleteById(anyLong());
    }

}
