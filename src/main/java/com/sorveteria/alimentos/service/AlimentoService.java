package com.sorveteria.alimentos.service;

import com.sorveteria.alimentos.dto.AlimentoDto;
import com.sorveteria.alimentos.model.Alimento;
import com.sorveteria.alimentos.repository.AlimentoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AlimentoService {

    @Autowired
    private AlimentoRepository alimentoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Page<AlimentoDto> obterTodos(Pageable paginacao) {
        return alimentoRepository.findAll(paginacao).map(p -> modelMapper.map(p, AlimentoDto.class));
    }

    public AlimentoDto obterPorId(Long id) {
        return modelMapper.map(alimentoRepository.findById(id).orElseThrow(EntityNotFoundException::new), AlimentoDto.class);
    }

    public AlimentoDto criaAlimento(AlimentoDto dto) {
        Alimento alimento = modelMapper.map(dto, Alimento.class);
        //set
        alimentoRepository.save(alimento);

        return modelMapper.map(alimento, AlimentoDto.class);
    }

    public AlimentoDto atualizaAlimento(Long id, AlimentoDto dto) {
        Alimento alimento = modelMapper.map(dto, Alimento.class);
        alimento.setId(id);
        alimento = alimentoRepository.save(alimento);
        return modelMapper.map(alimento, AlimentoDto.class);
    }

    public void excluirAlimento(Long id) {
        alimentoRepository.deleteById(id);
    }
}
