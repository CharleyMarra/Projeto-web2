package br.ifg.urt.gamercatalog_api.service;

import java.util.List;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.ifg.urt.gamercatalog_api.dto.request.JogoRequestDTO;
import br.ifg.urt.gamercatalog_api.dto.response.JogoResponseDTO;
import br.ifg.urt.gamercatalog_api.mapper.JogoMapper;
import br.ifg.urt.gamercatalog_api.model.Jogo;
import br.ifg.urt.gamercatalog_api.repository.JogoRepository;

@Service
public class JogoService {

    private static final Logger logger = Logger.getLogger(JogoService.class.getName());

    private final JogoRepository repository;
    private final JogoMapper mapper;

    public JogoService(JogoRepository repository, JogoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public JogoResponseDTO findById(Long id) {
        logger.info("Buscando jogo no banco com ID: " + id);
        Jogo jogo = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Jogo não encontrado"));
        return mapper.toResponseDTO(jogo);
    }

    public List<JogoResponseDTO> findAll() {
        logger.info("Buscando todos os jogos no banco.");
        return mapper.toResponseDTOList(repository.findAll());
    }

    public JogoResponseDTO create(JogoRequestDTO dto) {
        logger.info("Salvando novo jogo via DTO no banco.");
        Jogo jogo = mapper.toEntity(dto);
        
        // Se houver algum valor padrão/inicial que venha do DTO, pode-se tratar aqui. 
        // Nota: preco não foi mapeado no DTO enviado, caso precise, adicione-o no record.
        if(jogo.getPreco() == null) {
            jogo.setPreco(0.0); 
        }
        if(jogo.getClassificacaoIndicativa() == null) {
            jogo.setClassificacaoIndicativa(0);
        }

        Jogo salvo = repository.save(jogo);
        return mapper.toResponseDTO(salvo);
    }

    @Transactional
    public JogoResponseDTO update(Long id, JogoRequestDTO dto) {
        logger.info("Atualizando jogo ID: " + id);

        Jogo existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Jogo não encontrado"));

        existing.setTitulo(dto.nome()); // Mantendo a coerência com as regras do mapper
        existing.setDescricao(dto.descricao());
        existing.setGenero(dto.genero());

        Jogo atualizado = repository.save(existing);
        return mapper.toResponseDTO(atualizado);
    }

    public void delete(Long id) {
        logger.info("Removendo jogo ID: " + id);
        Jogo existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Jogo não encontrado"));
        repository.delete(existing);
    }
}