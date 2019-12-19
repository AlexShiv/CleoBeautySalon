package ru.asu.cleo.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ru.asu.cleo.web.rest.TestUtil;

public class PromoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Promo.class);
        Promo promo1 = new Promo();
        promo1.setId(1L);
        Promo promo2 = new Promo();
        promo2.setId(promo1.getId());
        assertThat(promo1).isEqualTo(promo2);
        promo2.setId(2L);
        assertThat(promo1).isNotEqualTo(promo2);
        promo1.setId(null);
        assertThat(promo1).isNotEqualTo(promo2);
    }
}
