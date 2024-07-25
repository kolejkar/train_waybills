package karol.train.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import karol.train_waybill.request.AuthRequest;
import karol.train_waybill.request.JwtResponse;
import karol.train_waybill.request.MessageResponse;
import karol.train_waybill.request.SignupRequest;
import karol.train_waybill.CompanyViewImpl;
import karol.train_waybill.component.JwtTokenUtil;
import karol.train_waybill.database.Company;
import karol.train_waybill.database.ERole;
import karol.train_waybill.database.Role;
import karol.train_waybill.mapper.CompanyViewMapper;
import karol.train_waybill.repository.CompanyRepository;
import karol.train_waybill.repository.RoleRepository;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;

//import io.swagger.v3.oas.annotations.tags.Tag;

//@Tag(tags = "Authentication")
@RestController
@RequiredArgsConstructor
@PreAuthorize("permitAll()") 
@RequestMapping("/view")
public class AuthController{
	
	@Autowired
	private final AuthenticationManager authenticationManager;
	@Autowired
	 private final JwtTokenUtil jwtTokenUtil;
	 
	 @Autowired
	 private final CompanyViewMapper companyViewMapper;
	 
	 @Autowired
	 private final CompanyRepository companyRepository;
	 	 
	 @Autowired
	 private final RoleRepository roleRepository;
	 
	 private final PasswordEncoder encoder;
	 
	 private final JwtTokenUtil jwtUtils;


	    @PostMapping("/login")
	    public ResponseEntity<JwtResponse> login(@RequestBody @Valid AuthRequest request) {
	        Authentication authentication = authenticationManager
			    .authenticate(
			        new UsernamePasswordAuthenticationToken(
			            request.getUsername(), request.getPassword()
			        )
			    );
			
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
			CompanyViewImpl companyDetails = (CompanyViewImpl) authentication.getPrincipal();
			String jwt = jwtUtils.generateAccessToken(companyDetails);
			List<String> roles = companyDetails.getAuthorities().stream()
					.map(item -> item.getAuthority())
					.collect(Collectors.toList());


			return ResponseEntity.ok(new JwtResponse(jwt, 
													 companyDetails.getId(),
													 companyDetails.getUsername(), 
													 roles));
	    }
	    
	    @PostMapping("/register")
		public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
			if (companyRepository.findByEmail(signUpRequest.getEmail()).isPresent()) {
				return ResponseEntity
						.badRequest()
						.body(new MessageResponse("Error: Email is already in use!"));
			}

			// Create new user's account
			Company company = new Company(signUpRequest.getEmail(),encoder.encode(signUpRequest.getPassword()),signUpRequest.getNazwa());

			Set<String> strRoles = signUpRequest.getRole();
			Set<Role> roles = new HashSet<>();

			if (strRoles == null) {
				Role railwayRole = roleRepository.findByName(ERole.ROLE_RAILWAY)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				roles.add(railwayRole);
			} else {
				strRoles.forEach(role -> {
					switch (role) {
					case "admin":
						Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(adminRole);

						break;
						
					default:
						Role userRole = roleRepository.findByName(ERole.ROLE_COMPANY)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(userRole);
					}
				});
			}

			company.setRoles(roles);
			companyRepository.save(company);

			return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
		}
}
