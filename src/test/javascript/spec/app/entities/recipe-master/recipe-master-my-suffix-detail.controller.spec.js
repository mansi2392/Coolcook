'use strict';

describe('Controller Tests', function() {

    describe('RecipeMaster Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockRecipeMaster, MockCategory, MockIngredientQtyMapping;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockRecipeMaster = jasmine.createSpy('MockRecipeMaster');
            MockCategory = jasmine.createSpy('MockCategory');
            MockIngredientQtyMapping = jasmine.createSpy('MockIngredientQtyMapping');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'RecipeMaster': MockRecipeMaster,
                'Category': MockCategory,
                'IngredientQtyMapping': MockIngredientQtyMapping
            };
            createController = function() {
                $injector.get('$controller')("RecipeMasterMySuffixDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'coolcookApp:recipeMasterUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
