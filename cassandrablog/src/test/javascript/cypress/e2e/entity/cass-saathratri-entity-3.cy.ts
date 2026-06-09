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

describe('CassSaathratriEntity3 e2e test', () => {
  const cassSaathratriEntity3PageUrl = '/cassandrablog/cass-saathratri-entity-3';
  const cassSaathratriEntity3PageUrlPattern = new RegExp('/cassandrablog/cass-saathratri-entity-3(\\?.*)?$');
  let username: string;
  let password: string;
  const cassSaathratriEntity3Sample = {
    compositeId: { entityType: 'sample-entityType-1', createdTimeId: '00000000-0000-4000-8000-000000000001' },
  };

  let cassSaathratriEntity3;

  before(() => {
    cy.credentials().then(credentials => {
      ({ username, password } = credentials);
    });
  });

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', /^\/services\/cassandrablog\/api\/cass-saathratri-entity-3-s\b/).as('entitiesRequest');
    cy.intercept('POST', '/services/cassandrablog/api/cass-saathratri-entity-3-s').as('postEntityRequest');
    cy.intercept('DELETE', '/services/cassandrablog/api/cass-saathratri-entity-3-s/*/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (cassSaathratriEntity3) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/cassandrablog/api/cass-saathratri-entity-3-s/${cassSaathratriEntity3.compositeId.entityType}/${cassSaathratriEntity3.compositeId.createdTimeId}`,
      }).then(() => {
        cassSaathratriEntity3 = undefined;
      });
    }
  });

  it('CassSaathratriEntity3s menu should load CassSaathratriEntity3s page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('cassandrablog/cass-saathratri-entity-3');
    cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('CassSaathratriEntity3').should('exist');
    cy.url().should('match', cassSaathratriEntity3PageUrlPattern);
  });

  describe('CassSaathratriEntity3 page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(cassSaathratriEntity3PageUrl);
        cy.wait('@entitiesRequest', { timeout: 30000 });
      });

      it('should load create CassSaathratriEntity3 page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/cassandrablog/cass-saathratri-entity-3/new$'));
        cy.getEntityCreateUpdateHeading('CassSaathratriEntity3');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cassSaathratriEntity3PageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/cassandrablog/api/cass-saathratri-entity-3-s',
          body: cassSaathratriEntity3Sample,
        }).then(({ body }) => {
          cassSaathratriEntity3 = body;

          cy.intercept(
            {
              method: 'GET',
              url: /^\/services\/cassandrablog\/api\/cass-saathratri-entity-3-s\b/,
              times: 1,
            },
            {
              statusCode: 200,
              body: [cassSaathratriEntity3],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(cassSaathratriEntity3PageUrl);

        cy.wait('@entitiesRequestInternal', { timeout: 30000 });
      });

      it('detail button click should load details CassSaathratriEntity3 page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('cassSaathratriEntity3');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cassSaathratriEntity3PageUrlPattern);
      });

      it('edit button click should load edit CassSaathratriEntity3 page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CassSaathratriEntity3');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cassSaathratriEntity3PageUrlPattern);
      });

      it('edit button click should load edit CassSaathratriEntity3 page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CassSaathratriEntity3');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cassSaathratriEntity3PageUrlPattern);
      });

      it('last delete button click should delete instance of CassSaathratriEntity3', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('cassSaathratriEntity3').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cassSaathratriEntity3PageUrlPattern);

        cassSaathratriEntity3 = undefined;
      });
    });
  });

  describe('new CassSaathratriEntity3 page', () => {
    beforeEach(() => {
      cy.visit(cassSaathratriEntity3PageUrl);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('CassSaathratriEntity3');
    });

    it('should create an instance of CassSaathratriEntity3', () => {
      cy.get(`[data-cy="entityType"]`).type('54d3a0a9-5a5d-48c8-bb23-9984a1052b41');
      cy.get(`[data-cy="entityType"]`).should('have.value', '54d3a0a9-5a5d-48c8-bb23-9984a1052b41');

      cy.get(`[data-cy="createdTimeId"]`).type('fea70b89-6c85-41cc-b183-9b1c20037a9f');
      cy.get(`[data-cy="createdTimeId"]`).invoke('val').should('match', new RegExp('fea70b89-6c85-41cc-b183-9b1c20037a9f'));

      cy.get(`[data-cy="entityName"]`).type('solicit');
      cy.get(`[data-cy="entityName"]`).should('have.value', 'solicit');

      cy.get(`[data-cy="entityDescription"]`).type('strictly obstruct');
      cy.get(`[data-cy="entityDescription"]`).should('have.value', 'strictly obstruct');

      cy.get(`[data-cy="entityCost"]`).type('26753.39');
      cy.get(`[data-cy="entityCost"]`).should('have.value', '26753.39');

      cy.get(`[data-cy="departureDate"]`).type('22772');
      cy.get(`[data-cy="departureDate"]`).should('have.value', '22772');
      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        cassSaathratriEntity3 = response.body;
      });
      cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', cassSaathratriEntity3PageUrlPattern);
    });

    it('should round-trip MAP/SET widget entries through POST', () => {
      cy.get(`[data-cy="entityType"]`).type('54d3a0a9-5a5d-48c8-bb23-9984a1052b41');
      cy.get(`[data-cy="entityType"]`).should('have.value', '54d3a0a9-5a5d-48c8-bb23-9984a1052b41');

      cy.get(`[data-cy="createdTimeId"]`).type('fea70b89-6c85-41cc-b183-9b1c20037a9f');
      cy.get(`[data-cy="createdTimeId"]`).invoke('val').should('match', new RegExp('fea70b89-6c85-41cc-b183-9b1c20037a9f'));

      cy.get(`[data-cy="entityName"]`).type('solicit');
      cy.get(`[data-cy="entityName"]`).should('have.value', 'solicit');

      cy.get(`[data-cy="entityDescription"]`).type('strictly obstruct');
      cy.get(`[data-cy="entityDescription"]`).should('have.value', 'strictly obstruct');

      cy.get(`[data-cy="entityCost"]`).type('26753.39');
      cy.get(`[data-cy="entityCost"]`).should('have.value', '26753.39');

      cy.get(`[data-cy="departureDate"]`).type('22772');
      cy.get(`[data-cy="departureDate"]`).should('have.value', '22772');

      cy.get(`[data-cy="tags-add-value"]`).type('rt-tags-value');
      cy.get(`[data-cy="tags-add-button"]`).click();

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        expect(response.body.tags, 'SET round-trip: tags').to.include('rt-tags-value');
        cassSaathratriEntity3 = response.body;
      });
    });

    it('should accept input on the tags SET widget add row', () => {
      cy.get(`[data-cy="tags-add-value"]`).type('sample-tags-1');
      cy.get(`[data-cy="tags-add-value"]`).should('have.value', 'sample-tags-1');
      cy.get(`[data-cy="tags-add-button"]`).should('not.be.disabled');
    });

    it('should edit a row in the tags widget via dialog', () => {
      cy.get(`[data-cy="tags-add-value"]`).type('edit-orig');
      cy.get(`[data-cy="tags-add-button"]`).click();
      cy.get(`[data-cy="tags-row-0-edit"]`).click();
      cy.get('mat-dialog-container').should('be.visible');
      cy.get('[data-cy="dialog-edit-value"]').clear();
      cy.get('[data-cy="dialog-edit-value"]').type('edit-new');
      cy.get('[data-cy="dialog-save-button"]').click();
      cy.get('mat-dialog-container').should('not.exist');
    });

    it('should delete a row in the tags widget', () => {
      cy.get(`[data-cy="tags-add-value"]`).type('delete-target');
      cy.get(`[data-cy="tags-add-button"]`).click();
      cy.get(`[data-cy="tags-row-0-edit"]`).should('exist');
      cy.get(`[data-cy="tags-row-0-delete"]`).click();
      cy.get(`[data-cy="tags-row-0-edit"]`).should('not.exist');
    });
  });

  it('should toggle the Cassandra search form', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem(cassSaathratriEntity3PageUrl.substring(1));
    cy.get('[data-cy="searchFormToggle"]', { timeout: 30000 }).click();
    cy.get('[data-cy="searchButton"]').should('be.visible');
  });

  it('should generate and reset a UUID via the form buttons', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem(cassSaathratriEntity3PageUrl.substring(1));
    cy.get(entityCreateButtonSelector, { timeout: 30000 }).click();
    // Generate fills a fresh UUID via the component's generateUUID()/generateTimeUUID().
    cy.get(`[data-cy="createdTimeId-generate"]`).click();
    cy.get(`[data-cy="createdTimeId"]`)
      .invoke('val')
      .should('match', /^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$/i);
    // Reset restores the (empty) saved value, clearing the field.
    cy.get(`[data-cy="createdTimeId-reset"]`).click();
    cy.get(`[data-cy="createdTimeId"]`).should('have.value', '');
  });
});
