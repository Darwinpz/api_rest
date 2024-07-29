package com.darwinpz.apirest;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.darwinpz.apirest.entity.Cliente;
import com.darwinpz.apirest.repository.ClienteRepository;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ClienteTest {

	@Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClienteRepository clienteRepository;

    @BeforeEach
    void setUp() {
        clienteRepository.deleteAll();
    }
    
    @Test
    void testCreateCliente() throws Exception {
        String clienteJson = "{ \"identificacion\": \"0705463420\", \"nombre\": \"Darwin Pilaloa\", \"genero\": \"Masculino\", \"edad\": 25, \"direccion\": \"Machala\", \"telefono\": \"0963739435\", \"clave\": \"12345\", \"estado\": true }";

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(clienteJson))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.identificacion").value("0705463420"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre").value("Darwin Pilaloa"))
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        System.out.println("Respuesta de Contenido : " + responseContent);
        
        Cliente cliente = clienteRepository.findAll().get(0);
        assert(cliente.getIdentificacion().equals("0705463420"));
        assert(cliente.getNombre().equals("Darwin Pilaloa"));
        
    }
    

}
