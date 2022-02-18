package com.vobi.bank.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.vobi.bank.domain.Customer;
import com.vobi.bank.domain.DocumentType;
import com.vobi.bank.repository.DocumentTypeRepository;

import lombok.extern.slf4j.Slf4j;


@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@Slf4j
class CustomerServiceIT {

    @Autowired
    CustomerService customerService;

    @Autowired
    DocumentTypeRepository documentTypeRepository;
    
    @Test
    @Order(1)
    void debeValidarLasDependencias() {
        assertNotNull(customerService);
        assertNotNull(documentTypeRepository);
    }
    
    @Test
    @Order(2)
    void debeCrearCustomer() throws Exception{
    	
    	//Arrange
    	Integer idDocumentType=1;
    	Integer idCustomer=14836554;
    
    	Customer customer=null;
    	DocumentType documentType=documentTypeRepository.findById(idDocumentType).get();
    	
    	customer=new Customer();
    	customer.setAddress("Avenida siempre viva 123");
    	customer.setCustId(idCustomer);
    	customer.setDocumentType(documentType);
    	customer.setEmail("prueba@gmail.com");
    	customer.setEnable("Y");
    	customer.setName("Homero Simpson");
    	customer.setPhone("55555555555");
    	customer.setToken("sdfsfdgsjkfhsjkdhfsjk");
    	
    	//Act
    	customer=customerService.save(customer);
    	
    	//Assert
    	 assertNotNull(customer,"El customer es nulo no se pudo grabar");
    }
    
    @Test
    @Order(3)
    void debeModificarUnCustomer() throws Exception {
    	//Arrange
    	Integer idCustomer=14836554;
    	Customer customer=null;
    	customer=customerService.findById(idCustomer).get();
    	customer.setEnable("N");
    	
    	//Act
    	customer=customerService.update(customer);
    	
    	//Assert
    	 assertNotNull(customer,"El customer es nulo no se pudo grabar");
    }
    
    @Test
    @Order(4)
    void debeBorrarUnCustomer() throws Exception {
    	//Arrange
    	Integer idCustomer=14836554;
    	Customer customer=null;
    	Optional<Customer> customerOptional=null;
    	
    	assertTrue(customerService.findById(idCustomer).isPresent(), "No encontró el Customer");
    	customer=customerService.findById(idCustomer).get();
    	
    	//Act
    	customerService.delete(customer);
    	customerOptional=customerService.findById(idCustomer);    	
    	
    	//Assert
    	 assertFalse(customerOptional.isPresent(),"El customer es nulo no se pudo grabar");
    }
    
    @Test
    @Order(5)
    void debeConsultarTodosLosCustomer() throws Exception {
    	//Arrange
    	List<Customer> customers=null;
    	
    	//Act
    	customers=customerService.findAll();
    	customers.forEach(customer->log.info(customer.getName()));
    	
    	//Assert
    	 assertFalse(customers.isEmpty(), "No consultó los Customer");
    }

}
