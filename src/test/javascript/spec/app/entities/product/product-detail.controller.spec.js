'use strict';

describe('Controller Tests', function() {

    describe('Product Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockProduct, MockProductDescription, MockPicture, MockCategory, MockSupplier, MockWishList, MockShopingCart, MockPromotion;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockProduct = jasmine.createSpy('MockProduct');
            MockProductDescription = jasmine.createSpy('MockProductDescription');
            MockPicture = jasmine.createSpy('MockPicture');
            MockCategory = jasmine.createSpy('MockCategory');
            MockSupplier = jasmine.createSpy('MockSupplier');
            MockWishList = jasmine.createSpy('MockWishList');
            MockShopingCart = jasmine.createSpy('MockShopingCart');
            MockPromotion = jasmine.createSpy('MockPromotion');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Product': MockProduct,
                'ProductDescription': MockProductDescription,
                'Picture': MockPicture,
                'Category': MockCategory,
                'Supplier': MockSupplier,
                'WishList': MockWishList,
                'ShopingCart': MockShopingCart,
                'Promotion': MockPromotion
            };
            createController = function() {
                $injector.get('$controller')("ProductDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'rhinobuyApp:productUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
