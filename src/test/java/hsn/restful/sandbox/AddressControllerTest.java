package hsn.restful.sandbox;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hsn.restful.sandbox.entity.Address;
import hsn.restful.sandbox.entity.Contact;
import hsn.restful.sandbox.entity.User;
import hsn.restful.sandbox.model.requests.CreateAddressRequest;
import hsn.restful.sandbox.model.requests.UpdateAddressRequest;
import hsn.restful.sandbox.model.responses.AddressResponse;
import hsn.restful.sandbox.model.responses.WebResponse;
import hsn.restful.sandbox.repository.AddressRepository;
import hsn.restful.sandbox.repository.ContactRepository;
import hsn.restful.sandbox.repository.UserRepository;
import hsn.restful.sandbox.security.BCrypt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AddressControllerTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        addressRepository.deleteAll();
        contactRepository.deleteAll();
        userRepository.deleteAll();

        User user = new User();
        user.setUsername("hasan");
        user.setName("Hasan Hasan");
        user.setPassword(BCrypt.hashpw("password", BCrypt.gensalt()));
        user.setToken("token");
        user.setTokenExpiredAt(System.currentTimeMillis() + 10000L);
        userRepository.save(user);

        Contact contact = new Contact();
        contact.setId("hasan");
        contact.setUser(user);
        contact.setFirstName("Hasan");
        contact.setLastName("Clay");
        contact.setEmail("mail@mail.com");
        contact.setPhone("08123456");
        contactRepository.save(contact);
    }

    @Test
    void createAddressBadRequest() throws Exception {
        CreateAddressRequest request = new CreateAddressRequest();
        request.setCountry("");

        mockMvc.perform(
                post("/api/contacts/hasan/addresses")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("X-API-TOKEN", "token")
        ).andExpectAll(
                status().isBadRequest()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNotNull(response.getErrors());
        });
    }

    @Test
    void createAddressSuccess() throws Exception {
        CreateAddressRequest request = new CreateAddressRequest();
        request.setCity("Jakarta Pusat");
        request.setStreet("Jl. Jalan");
        request.setProvince("DKI Jakarta");
        request.setPostalCode("11233");
        request.setCountry("Indonesia");

        mockMvc.perform(
                post("/api/contacts/hasan/addresses")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("X-API-TOKEN", "token")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<AddressResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNull(response.getErrors());
            assertEquals(request.getCity(), response.getData().getCity());
            assertEquals(request.getStreet(), response.getData().getStreet());
            assertEquals(request.getProvince(), response.getData().getProvince());
            assertEquals(request.getPostalCode(), response.getData().getPostalCode());
            assertEquals(request.getCountry(), response.getData().getCountry());

            assertTrue(addressRepository.existsById(response.getData().getId()));
        });
    }

    @Test
    void getAddressNotFound() throws Exception {
        mockMvc.perform(
                get("/api/contacts/hasan/addresses/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "token")
        ).andExpectAll(
                status().isNotFound()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNotNull(response.getErrors());
        });
    }

    @Test
    void getAddressSuccess() throws Exception {
        Contact contact = contactRepository.findById("hasan").orElse(null);

        Address address = new Address();
        address.setId("test");
        address.setContact(contact);
        address.setStreet("Jalan");
        address.setCity("Jakarta");
        address.setProvince("DKI Jakarta");
        address.setPostalCode("11233");
        address.setCountry("Indonesia");
        addressRepository.save(address);

        mockMvc.perform(
                get("/api/contacts/hasan/addresses/test")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "token")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<AddressResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNull(response.getErrors());
            assertEquals(address.getId(), response.getData().getId());
            assertEquals(address.getStreet(), response.getData().getStreet());
            assertEquals(address.getCity(), response.getData().getCity());
            assertEquals(address.getProvince(), response.getData().getProvince());
            assertEquals(address.getPostalCode(), response.getData().getPostalCode());
            assertEquals(address.getCountry(), response.getData().getCountry());
        });
    }

    @Test
    void updateAddressBadRequest() throws Exception {
        UpdateAddressRequest request = new UpdateAddressRequest();
        request.setCountry("");

        mockMvc.perform(
                put("/api/contacts/hasan/addresses/test")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("X-API-TOKEN", "token")
        ).andExpectAll(
                status().isBadRequest()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNotNull(response.getErrors());
        });
    }

    @Test
    void updateAddressSuccess() throws Exception {
        Contact contact = contactRepository.findById("hasan").orElse(null);

        Address address = new Address();
        address.setId("test");
        address.setContact(contact);
        address.setStreet("Jalan");
        address.setCity("Jakarta");
        address.setProvince("DKI Jakarta");
        address.setPostalCode("11233");
        address.setCountry("Indonesia");
        addressRepository.save(address);

        UpdateAddressRequest request = new UpdateAddressRequest();
        request.setCity("Jakarta Pusat - New");
        request.setStreet("Jl. Jalan - New");
        request.setProvince("DKI Jakarta - New");
        request.setPostalCode("33211");
        request.setCountry("Indonesia - New");

        mockMvc.perform(
                put("/api/contacts/hasan/addresses/test")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("X-API-TOKEN", "token")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<AddressResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNull(response.getErrors());
            assertEquals(request.getCity(), response.getData().getCity());
            assertEquals(request.getStreet(), response.getData().getStreet());
            assertEquals(request.getProvince(), response.getData().getProvince());
            assertEquals(request.getPostalCode(), response.getData().getPostalCode());
            assertEquals(request.getCountry(), response.getData().getCountry());

            assertTrue(addressRepository.existsById(response.getData().getId()));
        });
    }

    @Test
    void deleteAddressNotFound() throws Exception {
        mockMvc.perform(
                delete("/api/contacts/hasan/addresses/test")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "token")
        ).andExpectAll(
                status().isNotFound()
        ).andDo(result -> {
            WebResponse<AddressResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNotNull(response.getErrors());
        });
    }

    @Test
    void deleteAddressSuccess() throws Exception {
        Contact contact = contactRepository.findById("hasan").orElse(null);

        Address address = new Address();
        address.setId("test");
        address.setContact(contact);
        address.setStreet("Jalan");
        address.setCity("Jakarta");
        address.setProvince("DKI Jakarta");
        address.setPostalCode("11233");
        address.setCountry("Indonesia");
        addressRepository.save(address);

        mockMvc.perform(
                delete("/api/contacts/hasan/addresses/test")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "token")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNull(response.getErrors());
            assertEquals("OK", response.getData());
            assertFalse(addressRepository.existsById("test"));
        });
    }
}
