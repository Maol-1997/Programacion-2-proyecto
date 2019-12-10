package ar.edu.um.programacion2.principal.web.rest;

import ar.edu.um.programacion2.principal.PrincipalApp;
import ar.edu.um.programacion2.principal.domain.Compra;
import ar.edu.um.programacion2.principal.repository.CompraRepository;
import ar.edu.um.programacion2.principal.service.CompraService;
import ar.edu.um.programacion2.principal.service.dto.CompraDTO;
import ar.edu.um.programacion2.principal.service.mapper.CompraMapper;
import ar.edu.um.programacion2.principal.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static ar.edu.um.programacion2.principal.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CompraResource} REST controller.
 */
@SpringBootTest(classes = PrincipalApp.class)
public class CompraResourceIT {
//
//    private static final Float DEFAULT_PRECIO = 1F;
//    private static final Float UPDATED_PRECIO = 2F;
//    private static final Float SMALLER_PRECIO = 1F - 1F;
//
//    @Autowired
//    private CompraRepository compraRepository;
//
//    @Autowired
//    private CompraMapper compraMapper;
//
//    @Autowired
//    private CompraService compraService;
//
//    @Autowired
//    private MappingJackson2HttpMessageConverter jacksonMessageConverter;
//
//    @Autowired
//    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;
//
//    @Autowired
//    private ExceptionTranslator exceptionTranslator;
//
//    @Autowired
//    private EntityManager em;
//
//    @Autowired
//    private Validator validator;
//
//    private MockMvc restCompraMockMvc;
//
//    private Compra compra;
//
//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//        final CompraResource compraResource = new CompraResource(compraService);
//        this.restCompraMockMvc = MockMvcBuilders.standaloneSetup(compraResource)
//            .setCustomArgumentResolvers(pageableArgumentResolver)
//            .setControllerAdvice(exceptionTranslator)
//            .setConversionService(createFormattingConversionService())
//            .setMessageConverters(jacksonMessageConverter)
//            .setValidator(validator).build();
//    }
//
//    /**
//     * Create an entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static Compra createEntity(EntityManager em) {
//        Compra compra = new Compra()
//            .precio(DEFAULT_PRECIO);
//        return compra;
//    }
//    /**
//     * Create an updated entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static Compra createUpdatedEntity(EntityManager em) {
//        Compra compra = new Compra()
//            .precio(UPDATED_PRECIO);
//        return compra;
//    }
//
//    @BeforeEach
//    public void initTest() {
//        compra = createEntity(em);
//    }
//
//    @Test
//    @Transactional
//    public void createCompra() throws Exception {
//        int databaseSizeBeforeCreate = compraRepository.findAll().size();
//
//        // Create the Compra
//        CompraDTO compraDTO = compraMapper.toDto(compra);
//        restCompraMockMvc.perform(post("/api/compras")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(compraDTO)))
//            .andExpect(status().isCreated());
//
//        // Validate the Compra in the database
//        List<Compra> compraList = compraRepository.findAll();
//        assertThat(compraList).hasSize(databaseSizeBeforeCreate + 1);
//        Compra testCompra = compraList.get(compraList.size() - 1);
//        assertThat(testCompra.getPrecio()).isEqualTo(DEFAULT_PRECIO);
//    }
//
//    @Test
//    @Transactional
//    public void createCompraWithExistingId() throws Exception {
//        int databaseSizeBeforeCreate = compraRepository.findAll().size();
//
//        // Create the Compra with an existing ID
//        compra.setId(1L);
//        CompraDTO compraDTO = compraMapper.toDto(compra);
//
//        // An entity with an existing ID cannot be created, so this API call must fail
//        restCompraMockMvc.perform(post("/api/compras")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(compraDTO)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the Compra in the database
//        List<Compra> compraList = compraRepository.findAll();
//        assertThat(compraList).hasSize(databaseSizeBeforeCreate);
//    }
//
//
//    @Test
//    @Transactional
//    public void checkPrecioIsRequired() throws Exception {
//        int databaseSizeBeforeTest = compraRepository.findAll().size();
//        // set the field null
//        compra.setPrecio(null);
//
//        // Create the Compra, which fails.
//        CompraDTO compraDTO = compraMapper.toDto(compra);
//
//        restCompraMockMvc.perform(post("/api/compras")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(compraDTO)))
//            .andExpect(status().isBadRequest());
//
//        List<Compra> compraList = compraRepository.findAll();
//        assertThat(compraList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void getAllCompras() throws Exception {
//        // Initialize the database
//        compraRepository.saveAndFlush(compra);
//
//        // Get all the compraList
//        restCompraMockMvc.perform(get("/api/compras?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(compra.getId().intValue())))
//            .andExpect(jsonPath("$.[*].precio").value(hasItem(DEFAULT_PRECIO.doubleValue())));
//    }
//    
//    @Test
//    @Transactional
//    public void getCompra() throws Exception {
//        // Initialize the database
//        compraRepository.saveAndFlush(compra);
//
//        // Get the compra
//        restCompraMockMvc.perform(get("/api/compras/{id}", compra.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$.id").value(compra.getId().intValue()))
//            .andExpect(jsonPath("$.precio").value(DEFAULT_PRECIO.doubleValue()));
//    }
//
//    @Test
//    @Transactional
//    public void getNonExistingCompra() throws Exception {
//        // Get the compra
//        restCompraMockMvc.perform(get("/api/compras/{id}", Long.MAX_VALUE))
//            .andExpect(status().isNotFound());
//    }
//
//    @Test
//    @Transactional
//    public void updateCompra() throws Exception {
//        // Initialize the database
//        compraRepository.saveAndFlush(compra);
//
//        int databaseSizeBeforeUpdate = compraRepository.findAll().size();
//
//        // Update the compra
//        Compra updatedCompra = compraRepository.findById(compra.getId()).get();
//        // Disconnect from session so that the updates on updatedCompra are not directly saved in db
//        em.detach(updatedCompra);
//        updatedCompra
//            .precio(UPDATED_PRECIO);
//        CompraDTO compraDTO = compraMapper.toDto(updatedCompra);
//
//        restCompraMockMvc.perform(put("/api/compras")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(compraDTO)))
//            .andExpect(status().isOk());
//
//        // Validate the Compra in the database
//        List<Compra> compraList = compraRepository.findAll();
//        assertThat(compraList).hasSize(databaseSizeBeforeUpdate);
//        Compra testCompra = compraList.get(compraList.size() - 1);
//        assertThat(testCompra.getPrecio()).isEqualTo(UPDATED_PRECIO);
//    }
//
//    @Test
//    @Transactional
//    public void updateNonExistingCompra() throws Exception {
//        int databaseSizeBeforeUpdate = compraRepository.findAll().size();
//
//        // Create the Compra
//        CompraDTO compraDTO = compraMapper.toDto(compra);
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restCompraMockMvc.perform(put("/api/compras")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(compraDTO)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the Compra in the database
//        List<Compra> compraList = compraRepository.findAll();
//        assertThat(compraList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    public void deleteCompra() throws Exception {
//        // Initialize the database
//        compraRepository.saveAndFlush(compra);
//
//        int databaseSizeBeforeDelete = compraRepository.findAll().size();
//
//        // Delete the compra
//        restCompraMockMvc.perform(delete("/api/compras/{id}", compra.getId())
//            .accept(TestUtil.APPLICATION_JSON_UTF8))
//            .andExpect(status().isNoContent());
//
//        // Validate the database contains one less item
//        List<Compra> compraList = compraRepository.findAll();
//        assertThat(compraList).hasSize(databaseSizeBeforeDelete - 1);
//    }
//
//    @Test
//    @Transactional
//    public void equalsVerifier() throws Exception {
//        TestUtil.equalsVerifier(Compra.class);
//        Compra compra1 = new Compra();
//        compra1.setId(1L);
//        Compra compra2 = new Compra();
//        compra2.setId(compra1.getId());
//        assertThat(compra1).isEqualTo(compra2);
//        compra2.setId(2L);
//        assertThat(compra1).isNotEqualTo(compra2);
//        compra1.setId(null);
//        assertThat(compra1).isNotEqualTo(compra2);
//    }
//
//    @Test
//    @Transactional
//    public void dtoEqualsVerifier() throws Exception {
//        TestUtil.equalsVerifier(CompraDTO.class);
//        CompraDTO compraDTO1 = new CompraDTO();
//        compraDTO1.setId(1L);
//        CompraDTO compraDTO2 = new CompraDTO();
//        assertThat(compraDTO1).isNotEqualTo(compraDTO2);
//        compraDTO2.setId(compraDTO1.getId());
//        assertThat(compraDTO1).isEqualTo(compraDTO2);
//        compraDTO2.setId(2L);
//        assertThat(compraDTO1).isNotEqualTo(compraDTO2);
//        compraDTO1.setId(null);
//        assertThat(compraDTO1).isNotEqualTo(compraDTO2);
//    }
//
//    @Test
//    @Transactional
//    public void testEntityFromId() {
//        assertThat(compraMapper.fromId(42L).getId()).isEqualTo(42);
//        assertThat(compraMapper.fromId(null)).isNull();
//    }
}
