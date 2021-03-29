package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UserextraTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Userextra.class);
        Userextra userextra1 = new Userextra();
        userextra1.setId(1L);
        Userextra userextra2 = new Userextra();
        userextra2.setId(userextra1.getId());
        assertThat(userextra1).isEqualTo(userextra2);
        userextra2.setId(2L);
        assertThat(userextra1).isNotEqualTo(userextra2);
        userextra1.setId(null);
        assertThat(userextra1).isNotEqualTo(userextra2);
    }
}
