package com.airstrike.core.authentification

 open class RegisteredUser
 {
     var firstName : String
     var lastName : String
     var email : String
     var password : String
     var phoneNumber : String
     constructor(
         email: String,
         firstName: String,
         lastName: String,
         password: String,
         phoneNumber: String
     ) {
         this.email = email
         this.firstName = firstName
         this.lastName = lastName
         this.password = password
         this.phoneNumber = phoneNumber
     }
 }