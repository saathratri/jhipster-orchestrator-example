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

describe('CassAddOnsAvailableByOrganization e2e test', () => {
  const cassAddOnsAvailableByOrganizationPageUrl = '/cassandrablog/cass-add-ons-available-by-organization';
  const cassAddOnsAvailableByOrganizationPageUrlPattern = new RegExp('/cassandrablog/cass-add-ons-available-by-organization(\\?.*)?$');
  let username: string;
  let password: string;
  const cassAddOnsAvailableByOrganizationSample = {
    compositeId: {
      organizationId: '00000000-0000-4000-8000-000000000001',
      entityType: 'sample-entityType-1',
      entityId: '00000000-0000-4000-8000-000000000001',
      addOnId: '00000000-0000-4000-8000-000000000001',
    },
  };

  let cassAddOnsAvailableByOrganization;

  before(() => {
    cy.credentials().then(credentials => {
      ({ username, password } = credentials);
    });
  });

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', /^\/services\/cassandrablog\/api\/cass-add-ons-available-by-organizations\b/).as('entitiesRequest');
    cy.intercept('POST', '/services/cassandrablog/api/cass-add-ons-available-by-organizations').as('postEntityRequest');
    cy.intercept('DELETE', '/services/cassandrablog/api/cass-add-ons-available-by-organizations/*/*/*/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (cassAddOnsAvailableByOrganization) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/cassandrablog/api/cass-add-ons-available-by-organizations/${cassAddOnsAvailableByOrganization.compositeId.organizationId}/${cassAddOnsAvailableByOrganization.compositeId.entityType}/${cassAddOnsAvailableByOrganization.compositeId.entityId}/${cassAddOnsAvailableByOrganization.compositeId.addOnId}`,
      }).then(() => {
        cassAddOnsAvailableByOrganization = undefined;
      });
    }
  });

  it('CassAddOnsAvailableByOrganizations menu should load CassAddOnsAvailableByOrganizations page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('cassandrablog/cass-add-ons-available-by-organization');
    cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('CassAddOnsAvailableByOrganization').should('exist');
    cy.url().should('match', cassAddOnsAvailableByOrganizationPageUrlPattern);
  });

  describe('CassAddOnsAvailableByOrganization page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(cassAddOnsAvailableByOrganizationPageUrl);
        cy.wait('@entitiesRequest', { timeout: 30000 });
      });

      it('should load create CassAddOnsAvailableByOrganization page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/cassandrablog/cass-add-ons-available-by-organization/new$'));
        cy.getEntityCreateUpdateHeading('CassAddOnsAvailableByOrganization');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cassAddOnsAvailableByOrganizationPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/cassandrablog/api/cass-add-ons-available-by-organizations',
          body: cassAddOnsAvailableByOrganizationSample,
        }).then(({ body }) => {
          cassAddOnsAvailableByOrganization = body;

          cy.intercept(
            {
              method: 'GET',
              url: /^\/services\/cassandrablog\/api\/cass-add-ons-available-by-organizations\b/,
              times: 1,
            },
            {
              statusCode: 200,
              body: [cassAddOnsAvailableByOrganization],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(cassAddOnsAvailableByOrganizationPageUrl);

        cy.wait('@entitiesRequestInternal', { timeout: 30000 });
      });

      it('detail button click should load details CassAddOnsAvailableByOrganization page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('cassAddOnsAvailableByOrganization');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cassAddOnsAvailableByOrganizationPageUrlPattern);
      });

      it('edit button click should load edit CassAddOnsAvailableByOrganization page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CassAddOnsAvailableByOrganization');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cassAddOnsAvailableByOrganizationPageUrlPattern);
      });

      it('edit button click should load edit CassAddOnsAvailableByOrganization page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CassAddOnsAvailableByOrganization');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cassAddOnsAvailableByOrganizationPageUrlPattern);
      });

      it('last delete button click should delete instance of CassAddOnsAvailableByOrganization', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('cassAddOnsAvailableByOrganization').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cassAddOnsAvailableByOrganizationPageUrlPattern);

        cassAddOnsAvailableByOrganization = undefined;
      });
    });
  });

  describe('new CassAddOnsAvailableByOrganization page', () => {
    beforeEach(() => {
      cy.visit(cassAddOnsAvailableByOrganizationPageUrl);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('CassAddOnsAvailableByOrganization');
    });

    it('should create an instance of CassAddOnsAvailableByOrganization', () => {
      cy.get(`[data-cy="organizationId"]`).type('00000000-0000-4000-8000-000000000001');
      cy.get(`[data-cy="organizationId"]`).should('have.value', '00000000-0000-4000-8000-000000000001');

      cy.get(`[data-cy="entityType"]`).type('substitution immediately outside');
      cy.get(`[data-cy="entityType"]`).should('have.value', 'substitution immediately outside');

      cy.get(`[data-cy="entityId"]`).type('e1b52533-d4b8-4660-b655-45d703f2df78');
      cy.get(`[data-cy="entityId"]`).invoke('val').should('match', new RegExp('e1b52533-d4b8-4660-b655-45d703f2df78'));

      cy.get(`[data-cy="addOnId"]`).type('b440af1f-beda-447c-980d-a2a0a0ed4064');
      cy.get(`[data-cy="addOnId"]`).invoke('val').should('match', new RegExp('b440af1f-beda-447c-980d-a2a0a0ed4064'));

      cy.get(`[data-cy="addOnType"]`).type('for');
      cy.get(`[data-cy="addOnType"]`).should('have.value', 'for');
      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        cassAddOnsAvailableByOrganization = response.body;
      });
      cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', cassAddOnsAvailableByOrganizationPageUrlPattern);
    });

    it('should round-trip MAP/SET widget entries through POST', () => {
      cy.get(`[data-cy="organizationId"]`).type('00000000-0000-4000-8000-000000000001');
      cy.get(`[data-cy="organizationId"]`).should('have.value', '00000000-0000-4000-8000-000000000001');

      cy.get(`[data-cy="entityType"]`).type('substitution immediately outside');
      cy.get(`[data-cy="entityType"]`).should('have.value', 'substitution immediately outside');

      cy.get(`[data-cy="entityId"]`).type('e1b52533-d4b8-4660-b655-45d703f2df78');
      cy.get(`[data-cy="entityId"]`).invoke('val').should('match', new RegExp('e1b52533-d4b8-4660-b655-45d703f2df78'));

      cy.get(`[data-cy="addOnId"]`).type('b440af1f-beda-447c-980d-a2a0a0ed4064');
      cy.get(`[data-cy="addOnId"]`).invoke('val').should('match', new RegExp('b440af1f-beda-447c-980d-a2a0a0ed4064'));

      cy.get(`[data-cy="addOnType"]`).type('for');
      cy.get(`[data-cy="addOnType"]`).should('have.value', 'for');

      cy.get(`[data-cy="addOnDetailsText-add-key"]`).type('rt-addOnDetailsText-key');
      cy.get(`[data-cy="addOnDetailsText-add-value"]`).type('rt-addOnDetailsText-value');
      cy.get(`[data-cy="addOnDetailsText-add-button"]`).click();

      cy.get(`[data-cy="addOnDetailsDecimal-add-key"]`).type('rt-addOnDetailsDecimal-key');
      cy.get(`[data-cy="addOnDetailsDecimal-add-value"]`).type('99.99');
      cy.get(`[data-cy="addOnDetailsDecimal-add-button"]`).click();

      cy.get(`[data-cy="addOnDetailsBoolean-add-key"]`).type('rt-addOnDetailsBoolean-key');
      cy.get(`[data-cy="addOnDetailsBoolean-add-toggle"] button`).click({ force: true });
      cy.get(`[data-cy="addOnDetailsBoolean-add-toggle"] button`).click({ force: true });
      cy.get(`[data-cy="addOnDetailsBoolean-add-button"]`).click();

      cy.get(`[data-cy="addOnDetailsBigInt-add-key"]`).type('rt-addOnDetailsBigInt-key');
      cy.get(`[data-cy="addOnDetailsBigInt-add-datetime-generate"]`).click({ force: true });
      cy.get(`[data-cy="addOnDetailsBigInt-add-button"]`).click();

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        expect(response.body.addOnDetailsText, 'MAP<TEXT> round-trip: addOnDetailsText').to.have.property(
          'rt-addOnDetailsText-key',
          'rt-addOnDetailsText-value',
        );
        expect(response.body.addOnDetailsDecimal, 'MAP<DECIMAL> round-trip: addOnDetailsDecimal').to.have.property(
          'rt-addOnDetailsDecimal-key',
        );
        expect(response.body.addOnDetailsBoolean, 'MAP<BOOLEAN> round-trip: addOnDetailsBoolean').to.have.property(
          'rt-addOnDetailsBoolean-key',
          false,
        );
        expect(response.body.addOnDetailsBigInt, 'MAP<DAYJS> round-trip: addOnDetailsBigInt').to.have.property('rt-addOnDetailsBigInt-key');
        cassAddOnsAvailableByOrganization = response.body;
      });
    });

    it('should accept input on the addOnDetailsText MAP widget add row', () => {
      cy.get(`[data-cy="addOnDetailsText-add-key"]`).type('sample-key');
      cy.get(`[data-cy="addOnDetailsText-add-key"]`).should('have.value', 'sample-key');
      cy.get(`[data-cy="addOnDetailsText-add-value"]`).type('sample-value');
      cy.get(`[data-cy="addOnDetailsText-add-value"]`).should('have.value', 'sample-value');
      cy.get(`[data-cy="addOnDetailsText-add-button"]`).should('not.be.disabled');
    });

    it('should accept input on the addOnDetailsDecimal MAP widget add row', () => {
      cy.get(`[data-cy="addOnDetailsDecimal-add-key"]`).type('sample-key');
      cy.get(`[data-cy="addOnDetailsDecimal-add-key"]`).should('have.value', 'sample-key');
      cy.get(`[data-cy="addOnDetailsDecimal-add-value"]`).type('1001');
      cy.get(`[data-cy="addOnDetailsDecimal-add-value"]`).should('have.value', '1001');
      cy.get(`[data-cy="addOnDetailsDecimal-add-button"]`).should('not.be.disabled');
    });

    it('should accept input on the addOnDetailsBoolean MAP<BOOLEAN> widget add row', () => {
      cy.get(`[data-cy="addOnDetailsBoolean-add-key"]`).type('sample-key');
      cy.get(`[data-cy="addOnDetailsBoolean-add-key"]`).should('have.value', 'sample-key');
      cy.get(`[data-cy="addOnDetailsBoolean-add-toggle"] button`).click({ force: true });
      cy.get(`[data-cy="addOnDetailsBoolean-add-toggle"] button`).click({ force: true });
      cy.get(`[data-cy="addOnDetailsBoolean-add-button"]`).should('not.be.disabled');
    });

    it('should accept input on the addOnDetailsBigInt MAP<BIGINT/DATETIME> widget add row', () => {
      cy.get(`[data-cy="addOnDetailsBigInt-add-key"]`).type('sample-key');
      cy.get(`[data-cy="addOnDetailsBigInt-add-key"]`).should('have.value', 'sample-key');
      cy.get(`[data-cy="addOnDetailsBigInt-add-button"]`).should('exist');
    });

    it('should edit a row in the addOnDetailsText widget via dialog', () => {
      cy.get(`[data-cy="addOnDetailsText-add-key"]`).type('edit-addOnDetailsText-key');
      cy.get(`[data-cy="addOnDetailsText-add-value"]`).type('edit-orig');
      cy.get(`[data-cy="addOnDetailsText-add-button"]`).click();
      cy.get(`[data-cy="addOnDetailsText-row-edit-addOnDetailsText-key-edit"]`).click();
      cy.get('mat-dialog-container').should('be.visible');
      cy.get('[data-cy="dialog-edit-value"]').clear();
      cy.get('[data-cy="dialog-edit-value"]').type('edit-new');
      cy.get('[data-cy="dialog-save-button"]').click();
      cy.get('mat-dialog-container').should('not.exist');
    });

    it('should edit a row in the addOnDetailsDecimal widget via dialog', () => {
      cy.get(`[data-cy="addOnDetailsDecimal-add-key"]`).type('edit-addOnDetailsDecimal-key');
      cy.get(`[data-cy="addOnDetailsDecimal-add-value"]`).type('77.77');
      cy.get(`[data-cy="addOnDetailsDecimal-add-button"]`).click();
      cy.get(`[data-cy="addOnDetailsDecimal-row-edit-addOnDetailsDecimal-key-edit"]`).click();
      cy.get('mat-dialog-container').should('be.visible');
      cy.get('[data-cy="dialog-edit-value"]').clear();
      cy.get('[data-cy="dialog-edit-value"]').type('88.88');
      cy.get('[data-cy="dialog-save-button"]').click();
      cy.get('mat-dialog-container').should('not.exist');
    });

    it('should edit a row in the addOnDetailsBoolean widget via dialog', () => {
      cy.get(`[data-cy="addOnDetailsBoolean-add-key"]`).type('edit-addOnDetailsBoolean-key');
      cy.get(`[data-cy="addOnDetailsBoolean-add-toggle"] button`).click({ force: true });
      cy.get(`[data-cy="addOnDetailsBoolean-add-toggle"] button`).click({ force: true });
      cy.get(`[data-cy="addOnDetailsBoolean-add-button"]`).click();
      cy.get(`[data-cy="addOnDetailsBoolean-row-0-edit"]`).click();
      cy.get('mat-dialog-container').should('be.visible');
      cy.get('[data-cy="dialog-edit-toggle"] button').click({ force: true });
      cy.get('[data-cy="dialog-save-button"]').click();
      cy.get('mat-dialog-container').should('not.exist');
    });

    it('should delete a row in the addOnDetailsText widget', () => {
      cy.get(`[data-cy="addOnDetailsText-add-key"]`).type('del-addOnDetailsText-key');
      cy.get(`[data-cy="addOnDetailsText-add-value"]`).type('delete-val');
      cy.get(`[data-cy="addOnDetailsText-add-button"]`).click();
      cy.get(`[data-cy="addOnDetailsText-row-del-addOnDetailsText-key-edit"]`).should('exist');
      cy.get(`[data-cy="addOnDetailsText-row-del-addOnDetailsText-key-delete"]`).click();
      cy.get(`[data-cy="addOnDetailsText-row-del-addOnDetailsText-key-edit"]`).should('not.exist');
    });

    it('should delete a row in the addOnDetailsDecimal widget', () => {
      cy.get(`[data-cy="addOnDetailsDecimal-add-key"]`).type('del-addOnDetailsDecimal-key');
      cy.get(`[data-cy="addOnDetailsDecimal-add-value"]`).type('66.66');
      cy.get(`[data-cy="addOnDetailsDecimal-add-button"]`).click();
      cy.get(`[data-cy="addOnDetailsDecimal-row-del-addOnDetailsDecimal-key-edit"]`).should('exist');
      cy.get(`[data-cy="addOnDetailsDecimal-row-del-addOnDetailsDecimal-key-delete"]`).click();
      cy.get(`[data-cy="addOnDetailsDecimal-row-del-addOnDetailsDecimal-key-edit"]`).should('not.exist');
    });

    it('should delete a row in the addOnDetailsBoolean widget', () => {
      cy.get(`[data-cy="addOnDetailsBoolean-add-key"]`).type('del-addOnDetailsBoolean-key');
      cy.get(`[data-cy="addOnDetailsBoolean-add-toggle"] button`).click({ force: true });
      cy.get(`[data-cy="addOnDetailsBoolean-add-toggle"] button`).click({ force: true });
      cy.get(`[data-cy="addOnDetailsBoolean-add-button"]`).click();
      cy.get(`[data-cy="addOnDetailsBoolean-row-0-edit"]`).should('exist');
      cy.get(`[data-cy="addOnDetailsBoolean-row-0-delete"]`).click();
      cy.get(`[data-cy="addOnDetailsBoolean-row-0-edit"]`).should('not.exist');
    });

    it('should delete a row in the addOnDetailsBigInt widget', () => {
      cy.get(`[data-cy="addOnDetailsBigInt-add-key"]`).type('del-addOnDetailsBigInt-key');
      cy.get(`[data-cy="addOnDetailsBigInt-add-datetime-generate"]`).click({ force: true });
      cy.get(`[data-cy="addOnDetailsBigInt-add-button"]`).click();
      cy.get(`[data-cy="addOnDetailsBigInt-row-del-addOnDetailsBigInt-key-edit"]`).should('exist');
      cy.get(`[data-cy="addOnDetailsBigInt-row-del-addOnDetailsBigInt-key-delete"]`).click();
      cy.get(`[data-cy="addOnDetailsBigInt-row-del-addOnDetailsBigInt-key-edit"]`).should('not.exist');
    });
  });

  it('should toggle the Cassandra search form', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem(cassAddOnsAvailableByOrganizationPageUrl.substring(1));
    cy.get('[data-cy="searchFormToggle"]', { timeout: 30000 }).click();
    cy.get('[data-cy="searchButton"]').should('be.visible');
  });

  it('should generate and reset a UUID via the form buttons', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem(cassAddOnsAvailableByOrganizationPageUrl.substring(1));
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
