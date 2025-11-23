public enum AreaReservavel {
        ACADEMIA("Academia"),
        PISCINA("Piscina"),
        SALAO_FESTAS("Salão de Festas");

        private final String nome;

        AreaReservavel(String nome) {
            this.nome = nome;
        }

        public String getNome() {
            return nome;
        }

        @Override
        public String toString() {
            return nome;
        }
    }