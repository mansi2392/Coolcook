'use strict';

describe('Controller Tests', function() {

    describe('IngredientMaster Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockIngredientMaster;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockIngredientMaster = jasmine.createSpy('MockIngredientMaster');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'IngredientMaster': MockIngredientMaster
            };
            createController = function() {
                $injector.get('$controller')("IngredientMasterDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'coolcookApp:ingredientMasterUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
