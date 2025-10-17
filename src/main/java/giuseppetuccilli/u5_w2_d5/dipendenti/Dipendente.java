package giuseppetuccilli.u5_w2_d5.dipendenti;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "dipendenti")
@Getter

@NoArgsConstructor
public class Dipendente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Setter
    private String nome;
    @Setter
    private String cognome;
    @Setter
    private String email;
    private String username;
    @Setter
    private String avatar;

    public Dipendente(String nome, String cognome, String email) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.username = nome.substring(0, 2) + cognome.substring(0, 2);
        this.avatar = "";

    }

    public void setUsername() {
        this.username = this.nome.substring(0, 2) + this.cognome.substring(0, 2);
        ;
    }

    @Override
    public String toString() {
        return "Dipendente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
