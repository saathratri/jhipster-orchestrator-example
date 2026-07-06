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

describe('CassSaathratriEntity e2e test', () => {
  const cassSaathratriEntityPageUrl = '/cassandrablog/cass-saathratri-entity';
  const cassSaathratriEntityPageUrlPattern = new RegExp('/cassandrablog/cass-saathratri-entity(\\?.*)?$');
  let username: string;
  let password: string;
  const cassSaathratriEntitySample = { entityId: '00000000-0000-4000-8000-000000000001' };

  let cassSaathratriEntity;

  before(() => {
    cy.credentials().then(credentials => {
      ({ username, password } = credentials);
    });
  });

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', /^\/services\/cassandrablog\/api\/cass-saathratri-entities\b/).as('entitiesRequest');
    cy.intercept('POST', '/services/cassandrablog/api/cass-saathratri-entities').as('postEntityRequest');
    cy.intercept('DELETE', '/services/cassandrablog/api/cass-saathratri-entities/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (cassSaathratriEntity) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/cassandrablog/api/cass-saathratri-entities/${cassSaathratriEntity.entityId}`,
      }).then(() => {
        cassSaathratriEntity = undefined;
      });
    }
  });

  it('CassSaathratriEntities menu should load CassSaathratriEntities page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('cassandrablog/cass-saathratri-entity');
    cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('CassSaathratriEntity').should('exist');
    cy.url().should('match', cassSaathratriEntityPageUrlPattern);
  });

  describe('CassSaathratriEntity page', () => {
    it('should have translated page title', () => {
      cy.visit(cassSaathratriEntityPageUrl);
      cy.getEntityHeading('CassSaathratriEntity').should('not.contain', 'cassandrablogApp.cassandrablogCassSaathratriEntity.home.title');
    });

    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(cassSaathratriEntityPageUrl);
        cy.wait('@entitiesRequest', { timeout: 30000 });
      });

      it('should load create CassSaathratriEntity page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/cassandrablog/cass-saathratri-entity/new$'));
        cy.getEntityCreateUpdateHeading('CassSaathratriEntity');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cassSaathratriEntityPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/cassandrablog/api/cass-saathratri-entities',
          body: cassSaathratriEntitySample,
        }).then(({ body }) => {
          cassSaathratriEntity = body;

          cy.intercept(
            {
              method: 'GET',
              url: /^\/services\/cassandrablog\/api\/cass-saathratri-entities\b/,
              times: 1,
            },
            {
              statusCode: 200,
              body: [cassSaathratriEntity],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(cassSaathratriEntityPageUrl);

        cy.wait('@entitiesRequestInternal', { timeout: 30000 });
      });

      it('detail button click should load details CassSaathratriEntity page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('cassSaathratriEntity');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cassSaathratriEntityPageUrlPattern);
      });

      it('edit button click should load edit CassSaathratriEntity page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CassSaathratriEntity');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cassSaathratriEntityPageUrlPattern);
      });

      it('edit button click should load edit CassSaathratriEntity page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CassSaathratriEntity');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cassSaathratriEntityPageUrlPattern);
      });

      it('last delete button click should delete instance of CassSaathratriEntity', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('cassSaathratriEntity').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cassSaathratriEntityPageUrlPattern);

        cassSaathratriEntity = undefined;
      });
    });
  });

  describe('new CassSaathratriEntity page', () => {
    beforeEach(() => {
      cy.visit(cassSaathratriEntityPageUrl);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('CassSaathratriEntity');
    });

    it('should create an instance of CassSaathratriEntity', () => {
      cy.get(`[data-cy="entityId"]`).type('00000000-0000-4000-8000-000000000001');
      cy.get(`[data-cy="entityId"]`).should('have.value', '00000000-0000-4000-8000-000000000001');

      cy.get(`[data-cy="entityName"]`).type('drat');
      cy.get(`[data-cy="entityName"]`).should('have.value', 'drat');

      cy.get(`[data-cy="entityDescription"]`).type('excluding');
      cy.get(`[data-cy="entityDescription"]`).should('have.value', 'excluding');

      cy.get(`[data-cy="entityCost"]`).type('28369.56');
      cy.get(`[data-cy="entityCost"]`).should('have.value', '28369.56');

      cy.get(`[data-cy="createdId"]`).type('97daa705-c75e-47dc-b4a5-a3d16ff4af95');
      cy.get(`[data-cy="createdId"]`).invoke('val').should('match', new RegExp('97daa705-c75e-47dc-b4a5-a3d16ff4af95'));

      cy.get(`[data-cy="createdTimeId"]`).type('70fc2f32-d065-4605-ae92-908c451a51b3');
      cy.get(`[data-cy="createdTimeId"]`).invoke('val').should('match', new RegExp('70fc2f32-d065-4605-ae92-908c451a51b3'));

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        cassSaathratriEntity = response.body;
      });
      cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', cassSaathratriEntityPageUrlPattern);
    });
  });

  it('should generate and reset a UUID via the form buttons', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem(cassSaathratriEntityPageUrl.substring(1));
    cy.get(entityCreateButtonSelector, { timeout: 30000 }).click();
    // Generate fills a fresh UUID via the component's generateUUID()/generateTimeUUID().
    cy.get(`[data-cy="entityId-generate"]`).click();
    cy.get(`[data-cy="entityId"]`)
      .invoke('val')
      .should('match', /^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$/i);
    // Reset restores the (empty) saved value, clearing the field.
    cy.get(`[data-cy="entityId-reset"]`).click();
    cy.get(`[data-cy="entityId"]`).should('have.value', '');
  });
});
