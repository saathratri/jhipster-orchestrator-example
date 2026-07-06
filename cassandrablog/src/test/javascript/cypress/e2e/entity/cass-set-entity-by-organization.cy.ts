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

describe('CassSetEntityByOrganization e2e test', () => {
  const cassSetEntityByOrganizationPageUrl = '/cassandrablog/cass-set-entity-by-organization';
  const cassSetEntityByOrganizationPageUrlPattern = new RegExp('/cassandrablog/cass-set-entity-by-organization(\\?.*)?$');
  let username: string;
  let password: string;
  const cassSetEntityByOrganizationSample = { organizationId: '00000000-0000-4000-8000-000000000001' };

  let cassSetEntityByOrganization;

  before(() => {
    cy.credentials().then(credentials => {
      ({ username, password } = credentials);
    });
  });

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', /^\/services\/cassandrablog\/api\/cass-set-entity-by-organizations\b/).as('entitiesRequest');
    cy.intercept('POST', '/services/cassandrablog/api/cass-set-entity-by-organizations').as('postEntityRequest');
    cy.intercept('DELETE', '/services/cassandrablog/api/cass-set-entity-by-organizations/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (cassSetEntityByOrganization) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/cassandrablog/api/cass-set-entity-by-organizations/${cassSetEntityByOrganization.organizationId}`,
      }).then(() => {
        cassSetEntityByOrganization = undefined;
      });
    }
  });

  it('CassSetEntityByOrganizations menu should load CassSetEntityByOrganizations page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('cassandrablog/cass-set-entity-by-organization');
    cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('CassSetEntityByOrganization').should('exist');
    cy.url().should('match', cassSetEntityByOrganizationPageUrlPattern);
  });

  describe('CassSetEntityByOrganization page', () => {
    it('should have translated page title', () => {
      cy.visit(cassSetEntityByOrganizationPageUrl);
      cy.getEntityHeading('CassSetEntityByOrganization').should(
        'not.contain',
        'cassandrablogApp.cassandrablogCassSetEntityByOrganization.home.title',
      );
    });

    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(cassSetEntityByOrganizationPageUrl);
        cy.wait('@entitiesRequest', { timeout: 30000 });
      });

      it('should load create CassSetEntityByOrganization page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/cassandrablog/cass-set-entity-by-organization/new$'));
        cy.getEntityCreateUpdateHeading('CassSetEntityByOrganization');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cassSetEntityByOrganizationPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/cassandrablog/api/cass-set-entity-by-organizations',
          body: cassSetEntityByOrganizationSample,
        }).then(({ body }) => {
          cassSetEntityByOrganization = body;

          cy.intercept(
            {
              method: 'GET',
              url: /^\/services\/cassandrablog\/api\/cass-set-entity-by-organizations\b/,
              times: 1,
            },
            {
              statusCode: 200,
              body: [cassSetEntityByOrganization],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(cassSetEntityByOrganizationPageUrl);

        cy.wait('@entitiesRequestInternal', { timeout: 30000 });
      });

      it('detail button click should load details CassSetEntityByOrganization page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('cassSetEntityByOrganization');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cassSetEntityByOrganizationPageUrlPattern);
      });

      it('edit button click should load edit CassSetEntityByOrganization page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CassSetEntityByOrganization');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cassSetEntityByOrganizationPageUrlPattern);
      });

      it('edit button click should load edit CassSetEntityByOrganization page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CassSetEntityByOrganization');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cassSetEntityByOrganizationPageUrlPattern);
      });

      it('last delete button click should delete instance of CassSetEntityByOrganization', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('cassSetEntityByOrganization').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cassSetEntityByOrganizationPageUrlPattern);

        cassSetEntityByOrganization = undefined;
      });
    });
  });

  describe('new CassSetEntityByOrganization page', () => {
    beforeEach(() => {
      cy.visit(cassSetEntityByOrganizationPageUrl);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('CassSetEntityByOrganization');
    });

    it('should create an instance of CassSetEntityByOrganization', () => {
      cy.get(`[data-cy="organizationId"]`).type('00000000-0000-4000-8000-000000000001');
      cy.get(`[data-cy="organizationId"]`).should('have.value', '00000000-0000-4000-8000-000000000001');
      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        cassSetEntityByOrganization = response.body;
      });
      cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', cassSetEntityByOrganizationPageUrlPattern);
    });

    it('should round-trip MAP/SET widget entries through POST', () => {
      cy.get(`[data-cy="organizationId"]`).type('00000000-0000-4000-8000-000000000001');
      cy.get(`[data-cy="organizationId"]`).should('have.value', '00000000-0000-4000-8000-000000000001');

      cy.get(`[data-cy="tags-add-value"]`).type('rt-tags-value');
      cy.get(`[data-cy="tags-add-button"]`).click();

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        expect(response.body.tags, 'SET round-trip: tags').to.include('rt-tags-value');
        cassSetEntityByOrganization = response.body;
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

  it('should generate and reset a UUID via the form buttons', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem(cassSetEntityByOrganizationPageUrl.substring(1));
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
