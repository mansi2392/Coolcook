'use strict';

describe('Controller Tests', function() {

    describe('IngredientMaster Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockIngredientMaster, MockCategory;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockIngredientMaster = jasmine.createSpy('MockIngredientMaster');
            MockCategory = jasmine.createSpy('MockCategory');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'IngredientMaster': MockIngredientMaster,
                'Category': MockCategory
            };
            createController = function() {
                $injector.get('$controller')("IngredientMasterMySuffixDetailController", locals);
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
