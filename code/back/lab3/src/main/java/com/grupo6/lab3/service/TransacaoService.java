package com.grupo6.lab3.service;

import com.grupo6.lab3.dto.AlunoDTO;
import com.grupo6.lab3.dto.EmpresaDTO;
import com.grupo6.lab3.dto.ExtratoDTO;
import com.grupo6.lab3.dto.ProfessorDTO;
import com.grupo6.lab3.dto.ResgateVantagemDTO;
import com.grupo6.lab3.dto.TransferenciaPontosDTO;
import com.grupo6.lab3.entity.ResgateVantagem;
import com.grupo6.lab3.entity.TransferenciaPontos;
import com.grupo6.lab3.entity.Usuario;
import com.grupo6.lab3.entity.Vantagem;
import com.grupo6.lab3.repository.ResgateVantagemRepository;
import com.grupo6.lab3.repository.TransferenciaPontosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransacaoService {

    @Autowired
    private TransferenciaPontosRepository transferenciaPontosRepository;

    @Autowired
    private ResgateVantagemRepository resgateVantagemRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private VantagemService vantagemService;

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private EmpresaService empresaService;

    public List<TransferenciaPontos> getAllTransferencias() {
        return transferenciaPontosRepository.findAll();
    }

    public List<ResgateVantagem> getAllResgates() {
        return resgateVantagemRepository.findAll();
    }

    public Optional<TransferenciaPontos> createTransferencia(TransferenciaPontosDTO transferenciaDTO) {
        Optional<Usuario> origem = usuarioService.findById(transferenciaDTO.getOrigemId());
        Optional<Usuario> destino = usuarioService.findById(transferenciaDTO.getDestinoId());

        if (origem.isPresent() && destino.isPresent()) {
            TransferenciaPontos transferencia = new TransferenciaPontos();
            transferencia.setMoedas(transferenciaDTO.getMoedas());
            transferencia.setData(transferenciaDTO.getData());
            transferencia.setOrigem(origem.get());
            transferencia.setDestino(destino.get());
            transferencia.setDescricao(transferenciaDTO.getDescricao());
            return Optional.of(transferenciaPontosRepository.save(transferencia));
        }
        return Optional.empty();
    }

    public Optional<ResgateVantagem> createResgate(ResgateVantagemDTO resgateDTO) {
        Optional<Usuario> origem = usuarioService.findById(resgateDTO.getOrigemId());
        Optional<Usuario> destino = usuarioService.findById(resgateDTO.getDestinoId());
        Optional<Vantagem> vantagem = vantagemService.findById(resgateDTO.getVantagemId());

        if (origem.isPresent() && destino.isPresent() && vantagem.isPresent()) {
            ResgateVantagem resgate = new ResgateVantagem();
            resgate.setMoedas(resgateDTO.getMoedas());
            resgate.setData(resgateDTO.getData());
            resgate.setOrigem(origem.get());
            resgate.setDestino(destino.get());
            resgate.setDescricao(resgateDTO.getDescricao());
            resgate.setVantagem(vantagem.get());
            return Optional.of(resgateVantagemRepository.save(resgate));
        }
        return Optional.empty();
    }

    private String getUserName(Usuario usuario) {
        Optional<ProfessorDTO> professor = professorService.getProfessorById(usuario.getId());
        if (professor.isPresent()) {
            return professor.get().getNome();
        }

        Optional<AlunoDTO> aluno = alunoService.getAlunoById(usuario.getId());
        if (aluno.isPresent()) {
            return aluno.get().getNome();
        }

        Optional<EmpresaDTO> empresa = empresaService.getEmpresaById(usuario.getId());
        if (empresa.isPresent()) {
            return empresa.get().getNome();
        }

        return usuario.getLogin();
    }

    public List<ExtratoDTO> getExtratoByUsuarioId(Long usuarioId) {
        List<TransferenciaPontos> transferencias = 
            transferenciaPontosRepository.findByOrigemIdOrDestinoId(usuarioId, usuarioId);
        List<ResgateVantagem> resgates = 
            resgateVantagemRepository.findByOrigemIdOrDestinoId(usuarioId, usuarioId);
        
        List<ExtratoDTO> extrato = new ArrayList<>();
        
        for (TransferenciaPontos t : transferencias) {
            ExtratoDTO dto = new ExtratoDTO();
            dto.setId(t.getId());
            dto.setTipo("TRANSFERENCIA");
            dto.setMoedas(t.getMoedas());
            dto.setData(t.getData());
            dto.setDescricao(t.getDescricao());
            
            dto.setOrigem(getUserName(t.getOrigem()));
            dto.setOrigemId(t.getOrigem().getId());
            dto.setDestino(getUserName(t.getDestino()));
            dto.setDestinoId(t.getDestino().getId());
            extrato.add(dto);
        }
        
        for (ResgateVantagem r : resgates) {
            ExtratoDTO dto = new ExtratoDTO();
            dto.setId(r.getId());
            dto.setTipo("RESGATE");
            dto.setMoedas(r.getMoedas());
            dto.setData(r.getData());
            dto.setDescricao(r.getDescricao());
            dto.setOrigem(getUserName(r.getOrigem()));
            dto.setDestino(getUserName(r.getDestino()));
            dto.setVantagemNome(r.getVantagem().getNome());
            extrato.add(dto);
        }
        
        extrato.sort((a, b) -> b.getData().compareTo(a.getData()));
        
        return extrato;
    }
} 