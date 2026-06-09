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

describe('CassSaathratriEntity4 e2e test', () => {
  const cassSaathratriEntity4PageUrl = '/cassandrablog/cass-saathratri-entity-4';
  const cassSaathratriEntity4PageUrlPattern = new RegExp('/cassandrablog/cass-saathratri-entity-4(\\?.*)?$');
  let username: string;
  let password: string;
  const cassSaathratriEntity4Sample = {
    compositeId: { organizationId: '00000000-0000-4000-8000-000000000001', attributeKey: 'sample-attributeKey-1' },
  };

  let cassSaathratriEntity4;

  before(() => {
    cy.credentials().then(credentials => {
      ({ username, password } = credentials);
    });
  });

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', /^\/services\/cassandrablog\/api\/cass-saathratri-entity-4-s\b/).as('entitiesRequest');
    cy.intercept('POST', '/services/cassandrablog/api/cass-saathratri-entity-4-s').as('postEntityRequest');
    cy.intercept('DELETE', '/services/cassandrablog/api/cass-saathratri-entity-4-s/*/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (cassSaathratriEntity4) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/cassandrablog/api/cass-saathratri-entity-4-s/${cassSaathratriEntity4.compositeId.organizationId}/${cassSaathratriEntity4.compositeId.attributeKey}`,
      }).then(() => {
        cassSaathratriEntity4 = undefined;
      });
    }
  });

  it('CassSaathratriEntity4s menu should load CassSaathratriEntity4s page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('cassandrablog/cass-saathratri-entity-4');
    cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('CassSaathratriEntity4').should('exist');
    cy.url().should('match', cassSaathratriEntity4PageUrlPattern);
  });

  describe('CassSaathratriEntity4 page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(cassSaathratriEntity4PageUrl);
        cy.wait('@entitiesRequest', { timeout: 30000 });
      });

      it('should load create CassSaathratriEntity4 page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/cassandrablog/cass-saathratri-entity-4/new$'));
        cy.getEntityCreateUpdateHeading('CassSaathratriEntity4');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cassSaathratriEntity4PageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/cassandrablog/api/cass-saathratri-entity-4-s',
          body: cassSaathratriEntity4Sample,
        }).then(({ body }) => {
          cassSaathratriEntity4 = body;

          cy.intercept(
            {
              method: 'GET',
              url: /^\/services\/cassandrablog\/api\/cass-saathratri-entity-4-s\b/,
              times: 1,
            },
            {
              statusCode: 200,
              body: [cassSaathratriEntity4],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(cassSaathratriEntity4PageUrl);

        cy.wait('@entitiesRequestInternal', { timeout: 30000 });
      });

      it('detail button click should load details CassSaathratriEntity4 page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('cassSaathratriEntity4');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cassSaathratriEntity4PageUrlPattern);
      });

      it('edit button click should load edit CassSaathratriEntity4 page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CassSaathratriEntity4');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cassSaathratriEntity4PageUrlPattern);
      });

      it('edit button click should load edit CassSaathratriEntity4 page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CassSaathratriEntity4');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cassSaathratriEntity4PageUrlPattern);
      });

      it('last delete button click should delete instance of CassSaathratriEntity4', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('cassSaathratriEntity4').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cassSaathratriEntity4PageUrlPattern);

        cassSaathratriEntity4 = undefined;
      });
    });
  });

  describe('new CassSaathratriEntity4 page', () => {
    beforeEach(() => {
      cy.visit(cassSaathratriEntity4PageUrl);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('CassSaathratriEntity4');
    });

    it('should create an instance of CassSaathratriEntity4', () => {
      cy.get(`[data-cy="organizationId"]`).type('00000000-0000-4000-8000-000000000001');
      cy.get(`[data-cy="organizationId"]`).should('have.value', '00000000-0000-4000-8000-000000000001');

      cy.get(`[data-cy="attributeKey"]`).type('yet');
      cy.get(`[data-cy="attributeKey"]`).should('have.value', 'yet');

      cy.get(`[data-cy="attributeValue"]`).type('yet');
      cy.get(`[data-cy="attributeValue"]`).should('have.value', 'yet');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        cassSaathratriEntity4 = response.body;
      });
      cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', cassSaathratriEntity4PageUrlPattern);
    });
  });

  it('should toggle the Cassandra search form', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem(cassSaathratriEntity4PageUrl.substring(1));
    cy.get('[data-cy="searchFormToggle"]', { timeout: 30000 }).click();
    cy.get('[data-cy="searchButton"]').should('be.visible');
  });

  it('should generate and reset a UUID via the form buttons', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem(cassSaathratriEntity4PageUrl.substring(1));
    cy.get(entityCreateButtonSelector, { timeout: 30000 }).click();
    // Generate fills a fresh UUID via the component's generateUUID()/generateTimeUUID().
    cy.get(`[data-cy="organizationId-generate"]`).click();
    cy.get(`[data-cy="organizationId"]`)
      .invoke('val')
      .should('match', /^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$/i);
    // Reset restores the (empty) saved value, clearing the field.
    cy.get(`[data-cy="organizationId-reset"]`).click();
    cy.get(`[data-cy="organizationId"]`).should('have.value', '');
  });
});
