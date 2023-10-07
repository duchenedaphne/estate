package com.chatop.backend.services.rental;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.chatop.backend.jpo.rental.RentalJpo;
import com.chatop.backend.jpo.rental.RentalRequest;
import com.chatop.backend.jpo.rental.RentalResponse;
import com.chatop.backend.jpo.rental.RentalsResponse;
import com.chatop.backend.models.Rental;
import com.chatop.backend.models.User;
import com.chatop.backend.repositories.RentalRepository;
import com.chatop.backend.services.user.UserServiceImpl;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService {

    @Autowired
    private final RentalRepository rentalRepository;
    @Autowired
    private final UserServiceImpl userServiceImpl;
    private final String UPLOAD_DIR = "./src/main/resources/static/uploads/";

    @Override
    public Rental createRental(Rental rental) throws Exception {
        return rentalRepository.save(rental);
    }

    @Override
    public Rental getRental(Long id) throws Exception {
        
        Optional<Rental> rentalOptional = rentalRepository.findById(id);
        if (rentalOptional.isPresent()) {
            return rentalOptional.get();
        } else {
            throw new RuntimeException("Aucune location trouvée.");
        }
    }

    @Override
    public Rental updateRental(Rental rental) throws Exception {
        return rentalRepository.save(rental);
    }

    @Override
    public String deleteRental(Long id) throws Exception {        
        rentalRepository.deleteById(id);
        return "La location a bien été supprimée";
    }

    @Override
    public List<Rental> getAllRentals() throws Exception {
        return rentalRepository.findAll();
    }
    
    public ResponseEntity<?> fetchListRentals() {

        HttpStatus status;
        List<Rental> rentals;
        RentalJpo rentalJpo;
        List<RentalJpo> rentalsJpo;
        RentalsResponse rentalsResponse = new RentalsResponse();
        File rentalPicture;

        try {
            Thread.sleep(1000);
            rentals = getAllRentals();
            rentalsJpo = new ArrayList<>();
            
            for (Rental rental : rentals) {
                rentalJpo = new RentalJpo();
                
                rentalJpo.setId(rental.getId());
                rentalJpo.setName(rental.getName());
                rentalJpo.setSurface(rental.getSurface());
                rentalJpo.setPrice(rental.getPrice());
                rentalJpo.setPicture(rental.getPicture());
                rentalJpo.setDescription(rental.getDescription());
                rentalJpo.setOwner_id(rental.getOwner_id().getId());
                rentalJpo.setCreated_at(rental.getCreated_at());
                rentalJpo.setUpdated_at(rental.getUpdated_at());

                rentalPicture = new File(rental.getPicture());
                if (rentalPicture.exists())
                    rentalJpo.setPicture(rental.getPicture());
                
                rentalsJpo.add(rentalJpo);
            }          
            rentalsResponse.setRentals(rentalsJpo);
            return new ResponseEntity<RentalsResponse>(rentalsResponse, HttpStatus.OK);

        } catch (HttpStatusCodeException exception) {         
            status = exception.getStatusCode();
            return new ResponseEntity<String>("Échec de la récupération des locations.", status);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<String>("rentalsResponse", HttpStatus.OK);
    }

    public ResponseEntity<?> fetchRental(Long id) {

        Rental rental = new Rental();
        RentalJpo rentalJpo = new RentalJpo();
        HttpStatus status;

        try {
            rental = getRental(id);
            rentalJpo.setId(rental.getId());
            rentalJpo.setName(rental.getName());
            rentalJpo.setSurface(rental.getSurface());
            rentalJpo.setPrice(rental.getPrice());
            rentalJpo.setPicture(rental.getPicture());
            rentalJpo.setDescription(rental.getDescription());
            rentalJpo.setOwner_id(rental.getOwner_id().getId());
            rentalJpo.setCreated_at(rental.getCreated_at());
            rentalJpo.setUpdated_at(rental.getUpdated_at());
            
        } catch (HttpStatusCodeException exception) {
            status = exception.getStatusCode();
            return new ResponseEntity<String>("Échec de la récupération de la location.", status);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<RentalJpo>(rentalJpo, HttpStatus.OK);
    }

    public ResponseEntity<?> buildRental(
        RentalRequest rentalRequest,
        UserDetails userDetails
    ) {

        String fileName;
        HttpStatus status;
        User userApp = new User();
        Rental rental = new Rental();
        RentalResponse rentalResponse = new RentalResponse();  
        MultipartFile file;
        Path path;   
        String baseUrl;   

        rental.setName(rentalRequest.getName());
        rental.setSurface(rentalRequest.getSurface());
        rental.setPrice(rentalRequest.getPrice());
        rental.setDescription(rentalRequest.getDescription());
        rental.setCreated_at(new Date());
        rental.setUpdated_at(new Date());

        file = rentalRequest.getPicture();
        fileName = StringUtils.cleanPath(file.getOriginalFilename()); 

        File uploadDirectory = new File(UPLOAD_DIR);
        if (!uploadDirectory.exists())
            uploadDirectory.mkdirs();

        path = Paths.get(UPLOAD_DIR + fileName);

        try {
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            e.printStackTrace();
        }

        baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toString();
        rental.setPicture(baseUrl + "/uploads/" + fileName);

        try { 
            userApp = userServiceImpl.getUserByEmail(userDetails.getUsername());
            rental.setOwner_id(userApp);
            createRental(rental);

        } catch (HttpStatusCodeException exception) {
            status = exception.getStatusCode();
            return new ResponseEntity<String>("Échec de la création de la location.", status);

        } catch (Exception e) {
            e.printStackTrace();
        }
        rentalResponse.setMessage("La location a bien été créée");
        return new ResponseEntity<RentalResponse>(rentalResponse, HttpStatus.CREATED);
    }

    public ResponseEntity<?> onChangeRental(Long id, RentalRequest rentalRequest) {

        HttpStatus status;
        Rental rental;
        RentalResponse rentalResponse = new RentalResponse();
        
        try {
            rental = getRental(id);

            if (rentalRequest.getName() != null)
                rental.setName(rentalRequest.getName());
            if (rentalRequest.getSurface() > 0)
                rental.setSurface(rentalRequest.getSurface());
            if (rentalRequest.getPrice() > 0)
                rental.setPrice(rentalRequest.getPrice());
            if (rentalRequest.getDescription() != null)
            rental.setDescription(rentalRequest.getDescription());
            rental.setUpdated_at(new Date());

            updateRental(rental);

        } catch (HttpStatusCodeException exception) {
            status = exception.getStatusCode();
            return new ResponseEntity<String>("Échec de la modification de la location.", status);

        } catch (Exception e) {
            e.printStackTrace();
        }        
        rentalResponse.setMessage("La location a bien été modifiée !");
        return new ResponseEntity<RentalResponse>(rentalResponse, HttpStatus.OK);
    }
}
