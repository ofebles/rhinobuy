'use strict';

describe('Controller Tests', function() {

    describe('CustomerPaymentMethod Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockCustomerPaymentMethod, MockCustomer, MockReferencePaymentMethod;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockCustomerPaymentMethod = jasmine.createSpy('MockCustomerPaymentMethod');
            MockCustomer = jasmine.createSpy('MockCustomer');
            MockReferencePaymentMethod = jasmine.createSpy('MockReferencePaymentMethod');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'CustomerPaymentMethod': MockCustomerPaymentMethod,
                'Customer': MockCustomer,
                'ReferencePaymentMethod': MockReferencePaymentMethod
            };
            createController = function() {
                $injector.get('$controller')("CustomerPaymentMethodDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'rhinobuyApp:customerPaymentMethodUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
