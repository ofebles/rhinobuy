'use strict';

describe('Controller Tests', function() {

    describe('Invoice Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockInvoice, MockShipment, MockPayment, MockReferenceInvoiceStatus, MockOrders;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockInvoice = jasmine.createSpy('MockInvoice');
            MockShipment = jasmine.createSpy('MockShipment');
            MockPayment = jasmine.createSpy('MockPayment');
            MockReferenceInvoiceStatus = jasmine.createSpy('MockReferenceInvoiceStatus');
            MockOrders = jasmine.createSpy('MockOrders');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Invoice': MockInvoice,
                'Shipment': MockShipment,
                'Payment': MockPayment,
                'ReferenceInvoiceStatus': MockReferenceInvoiceStatus,
                'Orders': MockOrders
            };
            createController = function() {
                $injector.get('$controller')("InvoiceDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'rhinobuyApp:invoiceUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
