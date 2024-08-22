package hsn.restful.sandbox.controller;

import hsn.restful.sandbox.entity.User;
import hsn.restful.sandbox.model.requests.CreateAddressRequest;
import hsn.restful.sandbox.model.responses.AddressResponse;
import hsn.restful.sandbox.model.responses.WebResponse;
import hsn.restful.sandbox.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class AddressController {
    @Autowired
    AddressService addressService;

    @PostMapping(
            path = "/api/contacts/{contactId}/addresses",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<AddressResponse> create(User user,
                                               @RequestBody CreateAddressRequest request,
                                               @PathVariable("contactId") String contactId) {
        request.setContactId(contactId);
        AddressResponse addressResponse = addressService.create(user, request);
        return WebResponse.<AddressResponse>builder().data(addressResponse).build();
    }

    @GetMapping(
            path = "/api/contacts/{contactId}/addresses/{addressId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<AddressResponse> get(User user,
                                               @PathVariable("contactId") String contactId,
                                               @PathVariable("addressId") String addressId) {
        AddressResponse addressResponse = addressService.get(user, contactId, addressId);
        return WebResponse.<AddressResponse>builder().data(addressResponse).build();
    }
}
