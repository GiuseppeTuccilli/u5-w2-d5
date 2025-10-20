package giuseppetuccilli.u5_w2_d5.dipendenti;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DipendenteRepository extends JpaRepository<Dipendente, Long> {
    boolean existsByEmail(String email);

    Optional<Dipendente> findByEmail(String email);
}
