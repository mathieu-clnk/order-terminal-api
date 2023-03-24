package com.kamvity.samples.otm.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kamvity.samples.cm.entity.Customer;
import com.kamvity.samples.cm.entity.ResponseCustomer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class CustomerService {

    @Value(value="${endpoints.customer}/api/${endpoints.customer_version}/customers")
    private String customerEndpoint;
    private RestTemplate restTemplate = new RestTemplate();


    //TODO: replace by an auto-discovery URI at the app init

    public ResponseEntity<ResponseCustomer> createCustomer(Customer customer) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String url = customerEndpoint.concat("/create");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(customer);
            HttpEntity<String> httpEntity = new HttpEntity<>(json,headers);
            ResponseCustomer responseCustomer = restTemplate.postForObject(url,httpEntity, ResponseCustomer.class);
            HttpStatus status = responseCustomer != null && responseCustomer.getStatus().equals("success") ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status).body(responseCustomer);
        } catch (JsonProcessingException e) {
            ResponseCustomer responseCustomer = new ResponseCustomer();
            responseCustomer.setStatus("failed");
            responseCustomer.setErrorMessage("Cannot convert the given customer into a JSON object.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseCustomer);
        }

    }

    public ResponseEntity<ResponseCustomer> getByEmail(String email) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        //String url = this.uri+"/get-by-email";
        String url = customerEndpoint.concat("/get-by-email");
        HttpEntity<String> httpEntity = new HttpEntity<>(email,headers);
        ResponseCustomer responseCustomer = restTemplate.postForObject(url,httpEntity,ResponseCustomer.class);
        HttpStatus status = responseCustomer != null && responseCustomer.getStatus().equals("success") ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(responseCustomer);
    }

    public ResponseEntity<ResponseCustomer> getById(String id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String url = customerEndpoint.concat("/get-by-id");
        HttpEntity<String> httpEntity = new HttpEntity<>(id,headers);
        ResponseCustomer responseCustomer = restTemplate.postForObject(url,httpEntity,ResponseCustomer.class);
        HttpStatus status = responseCustomer != null && responseCustomer.getStatus().equals("success") ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(responseCustomer);
    }

    /*
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EntityManager entityManager;


    public ResponseEntity<Customer> getCustomerById(Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(customerRepository.findById(id).get());
    }

    public ResponseEntity<Customer> createCustomer(Customer customer) {
        return ResponseEntity.status(HttpStatus.OK).body(customerRepository.save(customer));
    }

    public ResponseEntity<Customer> findCustomerByEmail(String email) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Customer> query = criteriaBuilder.createQuery(Customer.class);
        Root<Customer> customerRoot = query.from(Customer.class);
        Predicate predicateCustomerEmail = criteriaBuilder.equal(customerRoot.get("email"),email);
        query.where(predicateCustomerEmail);
        List<Customer> list = entityManager.createQuery(query).getResultList();
        ResponseEntity response;
        if(list.isEmpty()) {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else if (list.size() > 1) {
            response = ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }else {
            response = ResponseEntity.status(HttpStatus.OK).body(list.get(0));
        }
        return response;

    }

     */
}
