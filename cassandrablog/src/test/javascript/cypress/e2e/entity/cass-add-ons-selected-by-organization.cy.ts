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

describe('CassAddOnsSelectedByOrganization e2e test', () => {
  const cassAddOnsSelectedByOrganizationPageUrl = '/cassandrablog/cass-add-ons-selected-by-organization';
  const cassAddOnsSelectedByOrganizationPageUrlPattern = new RegExp('/cassandrablog/cass-add-ons-selected-by-organization(\\?.*)?$');
  let username: string;
  let password: string;
  const cassAddOnsSelectedByOrganizationSample = {
    compositeId: {
      organizationId: '00000000-0000-4000-8000-000000000001',
      arrivalDate: 1001,
      accountNumber: 'sample-accountNumber-1',
      createdTimeId: '00000000-0000-4000-8000-000000000001',
    },
  };

  let cassAddOnsSelectedByOrganization;

  before(() => {
    cy.credentials().then(credentials => {
      ({ username, password } = credentials);
    });
  });

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', /^\/services\/cassandrablog\/api\/cass-add-ons-selected-by-organizations\b/).as('entitiesRequest');
    cy.intercept('POST', '/services/cassandrablog/api/cass-add-ons-selected-by-organizations').as('postEntityRequest');
    cy.intercept('DELETE', '/services/cassandrablog/api/cass-add-ons-selected-by-organizations/*/*/*/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (cassAddOnsSelectedByOrganization) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/cassandrablog/api/cass-add-ons-selected-by-organizations/${cassAddOnsSelectedByOrganization.compositeId.organizationId}/${cassAddOnsSelectedByOrganization.compositeId.arrivalDate}/${cassAddOnsSelectedByOrganization.compositeId.accountNumber}/${cassAddOnsSelectedByOrganization.compositeId.createdTimeId}`,
      }).then(() => {
        cassAddOnsSelectedByOrganization = undefined;
      });
    }
  });

  it('CassAddOnsSelectedByOrganizations menu should load CassAddOnsSelectedByOrganizations page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('cassandrablog/cass-add-ons-selected-by-organization');
    cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('CassAddOnsSelectedByOrganization').should('exist');
    cy.url().should('match', cassAddOnsSelectedByOrganizationPageUrlPattern);
  });

  describe('CassAddOnsSelectedByOrganization page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(cassAddOnsSelectedByOrganizationPageUrl);
        cy.wait('@entitiesRequest', { timeout: 30000 });
      });

      it('should load create CassAddOnsSelectedByOrganization page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/cassandrablog/cass-add-ons-selected-by-organization/new$'));
        cy.getEntityCreateUpdateHeading('CassAddOnsSelectedByOrganization');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cassAddOnsSelectedByOrganizationPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/cassandrablog/api/cass-add-ons-selected-by-organizations',
          body: cassAddOnsSelectedByOrganizationSample,
        }).then(({ body }) => {
          cassAddOnsSelectedByOrganization = body;

          cy.intercept(
            {
              method: 'GET',
              url: /^\/services\/cassandrablog\/api\/cass-add-ons-selected-by-organizations\b/,
              times: 1,
            },
            {
              statusCode: 200,
              body: [cassAddOnsSelectedByOrganization],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(cassAddOnsSelectedByOrganizationPageUrl);

        cy.wait('@entitiesRequestInternal', { timeout: 30000 });
      });

      it('detail button click should load details CassAddOnsSelectedByOrganization page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('cassAddOnsSelectedByOrganization');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cassAddOnsSelectedByOrganizationPageUrlPattern);
      });

      it('edit button click should load edit CassAddOnsSelectedByOrganization page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CassAddOnsSelectedByOrganization');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cassAddOnsSelectedByOrganizationPageUrlPattern);
      });

      it('edit button click should load edit CassAddOnsSelectedByOrganization page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CassAddOnsSelectedByOrganization');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cassAddOnsSelectedByOrganizationPageUrlPattern);
      });

      it('last delete button click should delete instance of CassAddOnsSelectedByOrganization', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('cassAddOnsSelectedByOrganization').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cassAddOnsSelectedByOrganizationPageUrlPattern);

        cassAddOnsSelectedByOrganization = undefined;
      });
    });
  });

  describe('new CassAddOnsSelectedByOrganization page', () => {
    beforeEach(() => {
      cy.visit(cassAddOnsSelectedByOrganizationPageUrl);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('CassAddOnsSelectedByOrganization');
    });

    it('should create an instance of CassAddOnsSelectedByOrganization', () => {
      cy.get(`[data-cy="organizationId"]`).type('00000000-0000-4000-8000-000000000001');
      cy.get(`[data-cy="organizationId"]`).should('have.value', '00000000-0000-4000-8000-000000000001');

      cy.get(`[data-cy="arrivalDate"]`).type('17244');
      cy.get(`[data-cy="arrivalDate"]`).should('have.value', '17244');

      cy.get(`[data-cy="accountNumber"]`).type('pause');
      cy.get(`[data-cy="accountNumber"]`).should('have.value', 'pause');

      cy.get(`[data-cy="createdTimeId"]`).type('477d9322-f9ae-4974-84ae-d057f2da4954');
      cy.get(`[data-cy="createdTimeId"]`).invoke('val').should('match', new RegExp('477d9322-f9ae-4974-84ae-d057f2da4954'));

      cy.get(`[data-cy="departureDate"]`).type('20427');
      cy.get(`[data-cy="departureDate"]`).should('have.value', '20427');

      cy.get(`[data-cy="customerId"]`).type('96723192-9002-41a9-bfcf-d936c1dd16cd');
      cy.get(`[data-cy="customerId"]`).invoke('val').should('match', new RegExp('96723192-9002-41a9-bfcf-d936c1dd16cd'));

      cy.get(`[data-cy="customerFirstName"]`).type('highlight er');
      cy.get(`[data-cy="customerFirstName"]`).should('have.value', 'highlight er');

      cy.get(`[data-cy="customerLastName"]`).type('spark classic');
      cy.get(`[data-cy="customerLastName"]`).should('have.value', 'spark classic');

      cy.get(`[data-cy="customerUpdatedEmail"]`).type('until unfortunate');
      cy.get(`[data-cy="customerUpdatedEmail"]`).should('have.value', 'until unfortunate');

      cy.get(`[data-cy="customerUpdatedPhoneNumber"]`).type('sermon sneaky encode');
      cy.get(`[data-cy="customerUpdatedPhoneNumber"]`).should('have.value', 'sermon sneaky encode');

      cy.get(`[data-cy="customerEstimatedArrivalTime"]`).type('blah easily until');
      cy.get(`[data-cy="customerEstimatedArrivalTime"]`).should('have.value', 'blah easily until');

      cy.get(`[data-cy="tinyUrlShortCode"]`).type('larva scornful');
      cy.get(`[data-cy="tinyUrlShortCode"]`).should('have.value', 'larva scornful');
      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        cassAddOnsSelectedByOrganization = response.body;
      });
      cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', cassAddOnsSelectedByOrganizationPageUrlPattern);
    });

    it('should round-trip MAP/SET widget entries through POST', () => {
      cy.get(`[data-cy="organizationId"]`).type('00000000-0000-4000-8000-000000000001');
      cy.get(`[data-cy="organizationId"]`).should('have.value', '00000000-0000-4000-8000-000000000001');

      cy.get(`[data-cy="arrivalDate"]`).type('17244');
      cy.get(`[data-cy="arrivalDate"]`).should('have.value', '17244');

      cy.get(`[data-cy="accountNumber"]`).type('pause');
      cy.get(`[data-cy="accountNumber"]`).should('have.value', 'pause');

      cy.get(`[data-cy="createdTimeId"]`).type('477d9322-f9ae-4974-84ae-d057f2da4954');
      cy.get(`[data-cy="createdTimeId"]`).invoke('val').should('match', new RegExp('477d9322-f9ae-4974-84ae-d057f2da4954'));

      cy.get(`[data-cy="departureDate"]`).type('20427');
      cy.get(`[data-cy="departureDate"]`).should('have.value', '20427');

      cy.get(`[data-cy="customerId"]`).type('96723192-9002-41a9-bfcf-d936c1dd16cd');
      cy.get(`[data-cy="customerId"]`).invoke('val').should('match', new RegExp('96723192-9002-41a9-bfcf-d936c1dd16cd'));

      cy.get(`[data-cy="customerFirstName"]`).type('highlight er');
      cy.get(`[data-cy="customerFirstName"]`).should('have.value', 'highlight er');

      cy.get(`[data-cy="customerLastName"]`).type('spark classic');
      cy.get(`[data-cy="customerLastName"]`).should('have.value', 'spark classic');

      cy.get(`[data-cy="customerUpdatedEmail"]`).type('until unfortunate');
      cy.get(`[data-cy="customerUpdatedEmail"]`).should('have.value', 'until unfortunate');

      cy.get(`[data-cy="customerUpdatedPhoneNumber"]`).type('sermon sneaky encode');
      cy.get(`[data-cy="customerUpdatedPhoneNumber"]`).should('have.value', 'sermon sneaky encode');

      cy.get(`[data-cy="customerEstimatedArrivalTime"]`).type('blah easily until');
      cy.get(`[data-cy="customerEstimatedArrivalTime"]`).should('have.value', 'blah easily until');

      cy.get(`[data-cy="tinyUrlShortCode"]`).type('larva scornful');
      cy.get(`[data-cy="tinyUrlShortCode"]`).should('have.value', 'larva scornful');

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
        cassAddOnsSelectedByOrganization = response.body;
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
    cy.clickOnEntityMenuItem(cassAddOnsSelectedByOrganizationPageUrl.substring(1));
    cy.get('[data-cy="searchFormToggle"]', { timeout: 30000 }).click();
    cy.get('[data-cy="searchButton"]').should('be.visible');
  });

  it('should generate and reset a UUID via the form buttons', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem(cassAddOnsSelectedByOrganizationPageUrl.substring(1));
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
