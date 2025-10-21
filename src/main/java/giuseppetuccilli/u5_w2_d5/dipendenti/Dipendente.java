package giuseppetuccilli.u5_w2_d5.dipendenti;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "dipendenti")
@Getter

@NoArgsConstructor
public class Dipendente implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Setter
    private String nome;
    @Setter
    private String password;
    @Setter
    private String email;
    private String username;
    @Setter
    private String avatar;
    @Setter
    @Enumerated
    private role ruolo;

    public Dipendente(String nome, String cognome, String email) {
        this.nome = nome;
        this.password = cognome;
        this.email = email;
        this.username = nome.substring(0, 2) + cognome.substring(0, 2);
        this.avatar = "";
        this.ruolo = role.USER;
    }

    public void setUsername() {
        this.username = this.nome.substring(0, 2) + this.password.substring(0, 2);
        ;
    }

    @Override
    public String toString() {
        return "Dipendente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cognome='" + password + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.ruolo.name()));
    }
}
