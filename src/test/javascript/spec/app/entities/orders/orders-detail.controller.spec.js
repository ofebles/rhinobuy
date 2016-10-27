'use strict';

describe('Controller Tests', function() {

    describe('Orders Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockOrders, MockInvoice, MockShipment, MockOrderItem, MockProduct;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockOrders = jasmine.createSpy('MockOrders');
            MockInvoice = jasmine.createSpy('MockInvoice');
            MockShipment = jasmine.createSpy('MockShipment');
            MockOrderItem = jasmine.createSpy('MockOrderItem');
            MockProduct = jasmine.createSpy('MockProduct');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Orders': MockOrders,
                'Invoice': MockInvoice,
                'Shipment': MockShipment,
                'OrderItem': MockOrderItem,
                'Product': MockProduct
            };
            createController = function() {
                $injector.get('$controller')("OrdersDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'rhinobuyApp:ordersUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
