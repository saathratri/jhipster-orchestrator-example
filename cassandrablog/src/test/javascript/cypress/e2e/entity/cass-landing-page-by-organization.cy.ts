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

describe('CassLandingPageByOrganization e2e test', () => {
  const cassLandingPageByOrganizationPageUrl = '/cassandrablog/cass-landing-page-by-organization';
  const cassLandingPageByOrganizationPageUrlPattern = new RegExp('/cassandrablog/cass-landing-page-by-organization(\\?.*)?$');
  let username: string;
  let password: string;
  const cassLandingPageByOrganizationSample = { organizationId: '00000000-0000-4000-8000-000000000001' };

  let cassLandingPageByOrganization;

  before(() => {
    cy.credentials().then(credentials => {
      ({ username, password } = credentials);
    });
  });

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', /^\/services\/cassandrablog\/api\/cass-landing-page-by-organizations\b/).as('entitiesRequest');
    cy.intercept('POST', '/services/cassandrablog/api/cass-landing-page-by-organizations').as('postEntityRequest');
    cy.intercept('DELETE', '/services/cassandrablog/api/cass-landing-page-by-organizations/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (cassLandingPageByOrganization) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/cassandrablog/api/cass-landing-page-by-organizations/${cassLandingPageByOrganization.organizationId}`,
      }).then(() => {
        cassLandingPageByOrganization = undefined;
      });
    }
  });

  it('CassLandingPageByOrganizations menu should load CassLandingPageByOrganizations page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('cassandrablog/cass-landing-page-by-organization');
    cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('CassLandingPageByOrganization').should('exist');
    cy.url().should('match', cassLandingPageByOrganizationPageUrlPattern);
  });

  describe('CassLandingPageByOrganization page', () => {
    it('should have translated page title', () => {
      cy.visit(cassLandingPageByOrganizationPageUrl);
      cy.getEntityHeading('CassLandingPageByOrganization').should(
        'not.contain',
        'cassandrablogApp.cassandrablogCassLandingPageByOrganization.home.title',
      );
    });

    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(cassLandingPageByOrganizationPageUrl);
        cy.wait('@entitiesRequest', { timeout: 30000 });
      });

      it('should load create CassLandingPageByOrganization page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/cassandrablog/cass-landing-page-by-organization/new$'));
        cy.getEntityCreateUpdateHeading('CassLandingPageByOrganization');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cassLandingPageByOrganizationPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/cassandrablog/api/cass-landing-page-by-organizations',
          body: cassLandingPageByOrganizationSample,
        }).then(({ body }) => {
          cassLandingPageByOrganization = body;

          cy.intercept(
            {
              method: 'GET',
              url: /^\/services\/cassandrablog\/api\/cass-landing-page-by-organizations\b/,
              times: 1,
            },
            {
              statusCode: 200,
              body: [cassLandingPageByOrganization],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(cassLandingPageByOrganizationPageUrl);

        cy.wait('@entitiesRequestInternal', { timeout: 30000 });
      });

      it('detail button click should load details CassLandingPageByOrganization page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('cassLandingPageByOrganization');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cassLandingPageByOrganizationPageUrlPattern);
      });

      it('edit button click should load edit CassLandingPageByOrganization page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CassLandingPageByOrganization');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cassLandingPageByOrganizationPageUrlPattern);
      });

      it('edit button click should load edit CassLandingPageByOrganization page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CassLandingPageByOrganization');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cassLandingPageByOrganizationPageUrlPattern);
      });

      it('last delete button click should delete instance of CassLandingPageByOrganization', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('cassLandingPageByOrganization').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cassLandingPageByOrganizationPageUrlPattern);

        cassLandingPageByOrganization = undefined;
      });
    });
  });

  describe('new CassLandingPageByOrganization page', () => {
    beforeEach(() => {
      cy.visit(cassLandingPageByOrganizationPageUrl);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('CassLandingPageByOrganization');
    });

    it('should create an instance of CassLandingPageByOrganization', () => {
      cy.get(`[data-cy="organizationId"]`).type('00000000-0000-4000-8000-000000000001');
      cy.get(`[data-cy="organizationId"]`).should('have.value', '00000000-0000-4000-8000-000000000001');
      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        cassLandingPageByOrganization = response.body;
      });
      cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', cassLandingPageByOrganizationPageUrlPattern);
    });

    it('should round-trip MAP/SET widget entries through POST', () => {
      cy.get(`[data-cy="organizationId"]`).type('00000000-0000-4000-8000-000000000001');
      cy.get(`[data-cy="organizationId"]`).should('have.value', '00000000-0000-4000-8000-000000000001');

      cy.get(`[data-cy="detailsText-add-key"]`).type('rt-detailsText-key');
      cy.get(`[data-cy="detailsText-add-value"]`).type('rt-detailsText-value');
      cy.get(`[data-cy="detailsText-add-button"]`).click();

      cy.get(`[data-cy="detailsDecimal-add-key"]`).type('rt-detailsDecimal-key');
      cy.get(`[data-cy="detailsDecimal-add-value"]`).type('99.99');
      cy.get(`[data-cy="detailsDecimal-add-button"]`).click();

      cy.get(`[data-cy="detailsBoolean-add-key"]`).type('rt-detailsBoolean-key');
      cy.get(`[data-cy="detailsBoolean-add-toggle"] button`).click({ force: true });
      cy.get(`[data-cy="detailsBoolean-add-toggle"] button`).click({ force: true });
      cy.get(`[data-cy="detailsBoolean-add-button"]`).click();

      cy.get(`[data-cy="detailsBigInt-add-key"]`).type('rt-detailsBigInt-key');
      cy.get(`[data-cy="detailsBigInt-add-datetime-generate"]`).click({ force: true });
      cy.get(`[data-cy="detailsBigInt-add-button"]`).click();

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        expect(response.body.detailsText, 'MAP<TEXT> round-trip: detailsText').to.have.property(
          'rt-detailsText-key',
          'rt-detailsText-value',
        );
        expect(response.body.detailsDecimal, 'MAP<DECIMAL> round-trip: detailsDecimal').to.have.property('rt-detailsDecimal-key');
        expect(response.body.detailsBoolean, 'MAP<BOOLEAN> round-trip: detailsBoolean').to.have.property('rt-detailsBoolean-key', false);
        expect(response.body.detailsBigInt, 'MAP<DAYJS> round-trip: detailsBigInt').to.have.property('rt-detailsBigInt-key');
        cassLandingPageByOrganization = response.body;
      });
    });

    it('should accept input on the detailsText MAP widget add row', () => {
      cy.get(`[data-cy="detailsText-add-key"]`).type('sample-key');
      cy.get(`[data-cy="detailsText-add-key"]`).should('have.value', 'sample-key');
      cy.get(`[data-cy="detailsText-add-value"]`).type('sample-value');
      cy.get(`[data-cy="detailsText-add-value"]`).should('have.value', 'sample-value');
      cy.get(`[data-cy="detailsText-add-button"]`).should('not.be.disabled');
    });

    it('should accept input on the detailsDecimal MAP widget add row', () => {
      cy.get(`[data-cy="detailsDecimal-add-key"]`).type('sample-key');
      cy.get(`[data-cy="detailsDecimal-add-key"]`).should('have.value', 'sample-key');
      cy.get(`[data-cy="detailsDecimal-add-value"]`).type('1001');
      cy.get(`[data-cy="detailsDecimal-add-value"]`).should('have.value', '1001');
      cy.get(`[data-cy="detailsDecimal-add-button"]`).should('not.be.disabled');
    });

    it('should accept input on the detailsBoolean MAP<BOOLEAN> widget add row', () => {
      cy.get(`[data-cy="detailsBoolean-add-key"]`).type('sample-key');
      cy.get(`[data-cy="detailsBoolean-add-key"]`).should('have.value', 'sample-key');
      cy.get(`[data-cy="detailsBoolean-add-toggle"] button`).click({ force: true });
      cy.get(`[data-cy="detailsBoolean-add-toggle"] button`).click({ force: true });
      cy.get(`[data-cy="detailsBoolean-add-button"]`).should('not.be.disabled');
    });

    it('should accept input on the detailsBigInt MAP<BIGINT/DATETIME> widget add row', () => {
      cy.get(`[data-cy="detailsBigInt-add-key"]`).type('sample-key');
      cy.get(`[data-cy="detailsBigInt-add-key"]`).should('have.value', 'sample-key');
      cy.get(`[data-cy="detailsBigInt-add-button"]`).should('exist');
    });

    it('should edit a row in the detailsText widget via dialog', () => {
      cy.get(`[data-cy="detailsText-add-key"]`).type('edit-detailsText-key');
      cy.get(`[data-cy="detailsText-add-value"]`).type('edit-orig');
      cy.get(`[data-cy="detailsText-add-button"]`).click();
      cy.get(`[data-cy="detailsText-row-edit-detailsText-key-edit"]`).click();
      cy.get('mat-dialog-container').should('be.visible');
      cy.get('[data-cy="dialog-edit-value"]').clear();
      cy.get('[data-cy="dialog-edit-value"]').type('edit-new');
      cy.get('[data-cy="dialog-save-button"]').click();
      cy.get('mat-dialog-container').should('not.exist');
    });

    it('should edit a row in the detailsDecimal widget via dialog', () => {
      cy.get(`[data-cy="detailsDecimal-add-key"]`).type('edit-detailsDecimal-key');
      cy.get(`[data-cy="detailsDecimal-add-value"]`).type('77.77');
      cy.get(`[data-cy="detailsDecimal-add-button"]`).click();
      cy.get(`[data-cy="detailsDecimal-row-edit-detailsDecimal-key-edit"]`).click();
      cy.get('mat-dialog-container').should('be.visible');
      cy.get('[data-cy="dialog-edit-value"]').clear();
      cy.get('[data-cy="dialog-edit-value"]').type('88.88');
      cy.get('[data-cy="dialog-save-button"]').click();
      cy.get('mat-dialog-container').should('not.exist');
    });

    it('should edit a row in the detailsBoolean widget via dialog', () => {
      cy.get(`[data-cy="detailsBoolean-add-key"]`).type('edit-detailsBoolean-key');
      cy.get(`[data-cy="detailsBoolean-add-toggle"] button`).click({ force: true });
      cy.get(`[data-cy="detailsBoolean-add-toggle"] button`).click({ force: true });
      cy.get(`[data-cy="detailsBoolean-add-button"]`).click();
      cy.get(`[data-cy="detailsBoolean-row-0-edit"]`).click();
      cy.get('mat-dialog-container').should('be.visible');
      cy.get('[data-cy="dialog-edit-toggle"] button').click({ force: true });
      cy.get('[data-cy="dialog-save-button"]').click();
      cy.get('mat-dialog-container').should('not.exist');
    });

    it('should delete a row in the detailsText widget', () => {
      cy.get(`[data-cy="detailsText-add-key"]`).type('del-detailsText-key');
      cy.get(`[data-cy="detailsText-add-value"]`).type('delete-val');
      cy.get(`[data-cy="detailsText-add-button"]`).click();
      cy.get(`[data-cy="detailsText-row-del-detailsText-key-edit"]`).should('exist');
      cy.get(`[data-cy="detailsText-row-del-detailsText-key-delete"]`).click();
      cy.get(`[data-cy="detailsText-row-del-detailsText-key-edit"]`).should('not.exist');
    });

    it('should delete a row in the detailsDecimal widget', () => {
      cy.get(`[data-cy="detailsDecimal-add-key"]`).type('del-detailsDecimal-key');
      cy.get(`[data-cy="detailsDecimal-add-value"]`).type('66.66');
      cy.get(`[data-cy="detailsDecimal-add-button"]`).click();
      cy.get(`[data-cy="detailsDecimal-row-del-detailsDecimal-key-edit"]`).should('exist');
      cy.get(`[data-cy="detailsDecimal-row-del-detailsDecimal-key-delete"]`).click();
      cy.get(`[data-cy="detailsDecimal-row-del-detailsDecimal-key-edit"]`).should('not.exist');
    });

    it('should delete a row in the detailsBoolean widget', () => {
      cy.get(`[data-cy="detailsBoolean-add-key"]`).type('del-detailsBoolean-key');
      cy.get(`[data-cy="detailsBoolean-add-toggle"] button`).click({ force: true });
      cy.get(`[data-cy="detailsBoolean-add-toggle"] button`).click({ force: true });
      cy.get(`[data-cy="detailsBoolean-add-button"]`).click();
      cy.get(`[data-cy="detailsBoolean-row-0-edit"]`).should('exist');
      cy.get(`[data-cy="detailsBoolean-row-0-delete"]`).click();
      cy.get(`[data-cy="detailsBoolean-row-0-edit"]`).should('not.exist');
    });

    it('should delete a row in the detailsBigInt widget', () => {
      cy.get(`[data-cy="detailsBigInt-add-key"]`).type('del-detailsBigInt-key');
      cy.get(`[data-cy="detailsBigInt-add-datetime-generate"]`).click({ force: true });
      cy.get(`[data-cy="detailsBigInt-add-button"]`).click();
      cy.get(`[data-cy="detailsBigInt-row-del-detailsBigInt-key-edit"]`).should('exist');
      cy.get(`[data-cy="detailsBigInt-row-del-detailsBigInt-key-delete"]`).click();
      cy.get(`[data-cy="detailsBigInt-row-del-detailsBigInt-key-edit"]`).should('not.exist');
    });
  });

  it('should generate and reset a UUID via the form buttons', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem(cassLandingPageByOrganizationPageUrl.substring(1));
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
