'use strict';

describe('Controller Tests', function() {

    describe('IngredientAtHome Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockIngredientAtHome, MockIngredientQtyMapping;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockIngredientAtHome = jasmine.createSpy('MockIngredientAtHome');
            MockIngredientQtyMapping = jasmine.createSpy('MockIngredientQtyMapping');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'IngredientAtHome': MockIngredientAtHome,
                'IngredientQtyMapping': MockIngredientQtyMapping
            };
            createController = function() {
                $injector.get('$controller')("IngredientAtHomeMySuffixDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'coolcookApp:ingredientAtHomeUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
