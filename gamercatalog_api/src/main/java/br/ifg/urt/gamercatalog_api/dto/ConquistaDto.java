    package br.ifg.urt.gamercatalog_api.dto;

    public class ConquistaDTO {

        private Long id;
        private String titulo;
        private String raridade;
        private Long jogoId;

        public ConquistaDTO() {
        }

        public ConquistaDTO(Long id, String titulo, String raridade, Long jogoId) {
            this.id = id;
            this.titulo = titulo;
            this.raridade = raridade;
            this.jogoId = jogoId;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getTitulo() {
            return titulo;
        }

        public void setTitulo(String titulo) {
            this.titulo = titulo;
        }

        public String getRaridade() {
            return raridade;
        }

        public void setRaridade(String raridade) {
            this.raridade = raridade;
        }

        public Long getJogoId() {
            return jogoId;
        }

        public void setJogoId(Long jogoId) {
            this.jogoId = jogoId;
        }
    }