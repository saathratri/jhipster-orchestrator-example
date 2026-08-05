import {
  entityConfirmDeleteButtonSelector,
  entityCreateButtonSelector,
  entityCreateCancelButtonSelector,
  entityCreateSaveButtonSelector,
  entityDeleteButtonSelector,
  entityDetailsBackButtonSelector,
  entityDetailsButtonSelector,
  entityEditButtonSelector,
  entityTableSelector,
} from '../../support/entity';

describe('CassSaathratriEntity2 e2e test', () => {
  const cassSaathratriEntity2PageUrl = '/cassandrablog/cass-saathratri-entity-2';
  const cassSaathratriEntity2PageUrlPattern = new RegExp('/cassandrablog/cass-saathratri-entity-2(\\?.*)?$');
  let username: string;
  let password: string;
  const cassSaathratriEntity2Sample = {
    compositeId: {
      entityTypeId: '00000000-0000-4000-8000-000000000001',
      yearOfDateAdded: 1001,
      arrivalDate: 1001,
      blogId: '00000000-0000-4000-8000-000000000001',
    },
  };

  let cassSaathratriEntity2;

  before(() => {
    cy.credentials().then(credentials => {
      ({ username, password } = credentials);
    });
  });

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', /^\/services\/cassandrablog\/api\/cass-saathratri-entity-2-s\b/).as('entitiesRequest');
    cy.intercept('POST', '/services/cassandrablog/api/cass-saathratri-entity-2-s').as('postEntityRequest');
    cy.intercept('DELETE', '/services/cassandrablog/api/cass-saathratri-entity-2-s/*/*/*/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (cassSaathratriEntity2) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/cassandrablog/api/cass-saathratri-entity-2-s/${cassSaathratriEntity2.compositeId.entityTypeId}/${cassSaathratriEntity2.compositeId.yearOfDateAdded}/${cassSaathratriEntity2.compositeId.arrivalDate}/${cassSaathratriEntity2.compositeId.blogId}`,
      }).then(() => {
        cassSaathratriEntity2 = undefined;
      });
    }
  });

  it('CassSaathratriEntity2s menu should load CassSaathratriEntity2s page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('cassandrablog/cass-saathratri-entity-2');
    cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('CassSaathratriEntity2').should('exist');
    cy.url().should('match', cassSaathratriEntity2PageUrlPattern);
  });

  describe('CassSaathratriEntity2 page', () => {
    it('should have translated page title', () => {
      cy.visit(cassSaathratriEntity2PageUrl);
      cy.getEntityHeading('CassSaathratriEntity2').should('not.contain', 'cassandrablogApp.cassandrablogCassSaathratriEntity2.home.title');
    });

    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(cassSaathratriEntity2PageUrl);
        cy.wait('@entitiesRequest', { timeout: 30000 });
      });

      it('should load create CassSaathratriEntity2 page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/cassandrablog/cass-saathratri-entity-2/new$'));
        cy.getEntityCreateUpdateHeading('CassSaathratriEntity2');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cassSaathratriEntity2PageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/cassandrablog/api/cass-saathratri-entity-2-s',
          body: cassSaathratriEntity2Sample,
        }).then(({ body }) => {
          cassSaathratriEntity2 = body;

          cy.intercept(
            {
              method: 'GET',
              url: /^\/services\/cassandrablog\/api\/cass-saathratri-entity-2-s\b/,
              times: 1,
            },
            {
              statusCode: 200,
              body: [cassSaathratriEntity2],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(cassSaathratriEntity2PageUrl);

        cy.wait('@entitiesRequestInternal', { timeout: 30000 });
      });

      it('detail button click should load details CassSaathratriEntity2 page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('cassSaathratriEntity2');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cassSaathratriEntity2PageUrlPattern);
      });

      it('edit button click should load edit CassSaathratriEntity2 page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CassSaathratriEntity2');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cassSaathratriEntity2PageUrlPattern);
      });

      it('edit button click should load edit CassSaathratriEntity2 page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CassSaathratriEntity2');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cassSaathratriEntity2PageUrlPattern);
      });

      it('last delete button click should delete instance of CassSaathratriEntity2', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('cassSaathratriEntity2').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cassSaathratriEntity2PageUrlPattern);

        cassSaathratriEntity2 = undefined;
      });
    });
  });

  describe('new CassSaathratriEntity2 page', () => {
    beforeEach(() => {
      cy.visit(cassSaathratriEntity2PageUrl);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('CassSaathratriEntity2');
    });

    it('should create an instance of CassSaathratriEntity2', () => {
      cy.get(`[data-cy="entityTypeId"]`).type('00000000-0000-4000-8000-000000000001');
      cy.get(`[data-cy="entityTypeId"]`).should('have.value', '00000000-0000-4000-8000-000000000001');

      cy.get(`[data-cy="yearOfDateAdded"]`).type('14310');
      cy.get(`[data-cy="yearOfDateAdded"]`).should('have.value', '14310');

      cy.get(`[data-cy="arrivalDate"]`).type('1454');
      cy.get(`[data-cy="arrivalDate"]`).should('have.value', '1454');

      cy.get(`[data-cy="blogId"]`).type('afaf3550-a60b-4229-9393-fc2945560d33');
      cy.get(`[data-cy="blogId"]`).invoke('val').should('match', new RegExp('afaf3550-a60b-4229-9393-fc2945560d33'));

      cy.get(`[data-cy="entityName"]`).type('unfinished with');
      cy.get(`[data-cy="entityName"]`).should('have.value', 'unfinished with');

      cy.get(`[data-cy="entityDescription"]`).type('hunt solidly');
      cy.get(`[data-cy="entityDescription"]`).should('have.value', 'hunt solidly');

      cy.get(`[data-cy="entityCost"]`).type('2653.32');
      cy.get(`[data-cy="entityCost"]`).should('have.value', '2653.32');

      cy.get(`[data-cy="departureDate"]`).type('12559');
      cy.get(`[data-cy="departureDate"]`).should('have.value', '12559');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        cassSaathratriEntity2 = response.body;
      });
      cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', cassSaathratriEntity2PageUrlPattern);
    });
  });

  it('should toggle the Cassandra search form', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem(cassSaathratriEntity2PageUrl.substring(1));
    cy.get('[data-cy="searchFormToggle"]', { timeout: 30000 }).click();
    cy.get('[data-cy="searchButton"]').should('be.visible');
  });

  it('should generate and reset a UUID via the form buttons', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem(cassSaathratriEntity2PageUrl.substring(1));
    cy.get(entityCreateButtonSelector, { timeout: 30000 }).click();
    // Generate fills a fresh UUID via the component's generateUUID()/generateTimeUUID().
    cy.get(`[data-cy="entityTypeId-generate"]`).click();
    cy.get(`[data-cy="entityTypeId"]`)
      .invoke('val')
      .should('match', /^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$/i);
    // Reset restores the (empty) saved value, clearing the field.
    cy.get(`[data-cy="entityTypeId-reset"]`).click();
    cy.get(`[data-cy="entityTypeId"]`).should('have.value', '');
  });
});
