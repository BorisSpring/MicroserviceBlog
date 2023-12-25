package com.main.gatewayblog.controller;

//@RestController
//@RequestMapping("/users")
public class UserController {

//    @PreAuthorize("hasRole('superr') or #id == #jwt.subject")
//    @DeleteMapping(path = "/{id}")
//    public String getDeleteId(@PathVariable("id") String id, @AuthenticationPrincipal Jwt jwt){
//        return  "U wanted to delete user with id " + id + " Jwt Subject: " + jwt.getSubject() + " , Sid:" + jwt.getClaims().get("sid") ;
//    }
//
//    @PostAuthorize("returnObject.id = #jwt.subject")
//    @GetMapping("/{id}")
//    public UserRest getUserDetails(@PathVariable String id, @AuthenticationPrincipal Jwt jwt){
//        return  new UserRest((String) jwt.getClaims().get("given_name"), (String) jwt.getClaims().get("family_name"), jwt.getSubject());
//    }
}
