'use strict';

App.controller('CustomerController', ['$scope', 'CustomerService', function($scope, CustomerService) {
          var self = this;
          self.customer={Id:null,lastName:null,password:''};
          self.customers=[];
              
          self.fetchAllCustomers = function(){
              CustomerService.fetchAllCustomers()
                  .then(
      					       function(d) {
      						        self.customers = d;
      					       },
            					function(errResponse){
            						console.error('Error while fetching Customers');
            					}
      			       );
          };
           
          self.createCustomer = function(customer){
              CustomerService.createCustomer(customer)
		              .then(
                      self.fetchAllCustomers, 
				              function(errResponse){
					               console.error('Error while creating Customer.');
				              }	
                  );
          };

         self.updateCustomer = function(customer){
              CustomerService.updateCustomer(customer)
              .then(
                      self.fetchAllCustomers, 
				              function(errResponse){
					               console.error('Error while updating Customer.');
				              }	
                  );
          };

         self.deleteCustomer = function(Id){
              CustomerService.deleteCustomer(Id)
		              .then(
				              self.fetchAllCustomers, 
				              function(errResponse){
					               console.error('Error while deleting Customer.');
				              }	
                  );
          };
          
          /*
           * Login Function
           */
          self.loginCustomer = function(customer){
              CustomerService.loginCustomer(customer)
              .then(
                      self.fetchAllCustomers, 
				              function(errResponse){
					               console.error('Error while logging Customer.');
				              }	
                  );
          };

          self.fetchAllCustomers();

          self.submit = function() {
              if(self.customer.Id==null){
                  console.log('Saving New Customer', self.customer);    
                  self.createCustomer(self.customer);
              }else{
                  console.log('Customer updating with id ', self.customer.Id);
                  console.log('Customer: ', self.customer);
                  self.updateCustomer(self.customer);
              }
              self.reset();
          };
          
          /*
           * When Login button presses : Check Login Credentials 
           */
          
          self.login = function(lastName){
        	  self.loginCustomer(self.customer);
        	  /*if(self.customer.lastName!=null){
        		  for(var i=0;i<self.customers.length;i++){
        			  if(self.customers[i].lastName==lastName){
        				  console.log('Customer lastName is ', self.customer.lastName);
                          console.log('Customer: ', self.customer);
        				  self.loginCustomer(self.customer);
        				  break;
        			  }else{
        				  console.log('LastName is not present in Database');
        				  break;
        			  }
        		  }
        	  }else{
        		  console.log('Login Failed. Try Again.');
        	  }*/
          };
              
          self.edit = function(Id){
              console.log('id to be edited', Id);
              for(var i = 0; i < self.customers.length; i++){
                  if(self.customers[i].Id == Id) {
                     self.customer = angular.copy(self.customers[i]);
                     break;
                  }
              }
          };
              
          self.remove = function(Id){
              console.log('id to be deleted', Id);
              for(var i = 0; i < self.customers.length; i++){
                  if(self.customers[i].Id == Id) {
                     self.reset();
                     break;
                  }
              }
              self.deleteCustomer(Id);
          };

          
          self.reset = function(){
              self.customer={Id:null,lastName:'',password:''};
              $scope.myForm.$setPristine(); //reset Form
          };

      }]);

