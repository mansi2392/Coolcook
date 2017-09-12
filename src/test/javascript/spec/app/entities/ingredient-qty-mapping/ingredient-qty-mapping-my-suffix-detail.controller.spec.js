'use strict';

describe('Controller Tests', function() {

    describe('IngredientQtyMapping Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockIngredientQtyMapping, MockQuantity, MockIngredientMaster;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockIngredientQtyMapping = jasmine.createSpy('MockIngredientQtyMapping');
            MockQuantity = jasmine.createSpy('MockQuantity');
            MockIngredientMaster = jasmine.createSpy('MockIngredientMaster');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'IngredientQtyMapping': MockIngredientQtyMapping,
                'Quantity': MockQuantity,
                'IngredientMaster': MockIngredientMaster
            };
            createController = function() {
                $injector.get('$controller')("IngredientQtyMappingMySuffixDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'coolcookApp:ingredientQtyMappingUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
