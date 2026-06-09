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

describe('CassReport e2e test', () => {
  const cassReportPageUrl = '/cassandrastore/cass-report';
  const cassReportPageUrlPattern = new RegExp('/cassandrastore/cass-report(\\?.*)?$');
  let username: string;
  let password: string;
  const cassReportSample = {
    id: '00000000-0000-4000-8000-000000000001',
    fileName: 'magnetize',
    fileExtension: 'notwithstanding',
    createDate: 2027,
  };

  let cassReport;

  before(() => {
    cy.credentials().then(credentials => {
      ({ username, password } = credentials);
    });
  });

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', /^\/services\/cassandrastore\/api\/cass-reports\b/).as('entitiesRequest');
    cy.intercept('POST', '/services/cassandrastore/api/cass-reports').as('postEntityRequest');
    cy.intercept('DELETE', '/services/cassandrastore/api/cass-reports/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (cassReport) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/cassandrastore/api/cass-reports/${cassReport.id}`,
      }).then(() => {
        cassReport = undefined;
      });
    }
  });

  it('CassReports menu should load CassReports page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('cassandrastore/cass-report');
    cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('CassReport').should('exist');
    cy.url().should('match', cassReportPageUrlPattern);
  });

  describe('CassReport page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(cassReportPageUrl);
        cy.wait('@entitiesRequest', { timeout: 30000 });
      });

      it('should load create CassReport page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/cassandrastore/cass-report/new$'));
        cy.getEntityCreateUpdateHeading('CassReport');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cassReportPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/cassandrastore/api/cass-reports',
          body: cassReportSample,
        }).then(({ body }) => {
          cassReport = body;

          cy.intercept(
            {
              method: 'GET',
              url: /^\/services\/cassandrastore\/api\/cass-reports\b/,
              times: 1,
            },
            {
              statusCode: 200,
              body: [cassReport],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(cassReportPageUrl);

        cy.wait('@entitiesRequestInternal', { timeout: 30000 });
      });

      it('detail button click should load details CassReport page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('cassReport');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cassReportPageUrlPattern);
      });

      it('edit button click should load edit CassReport page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CassReport');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cassReportPageUrlPattern);
      });

      it('edit button click should load edit CassReport page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CassReport');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cassReportPageUrlPattern);
      });

      it('last delete button click should delete instance of CassReport', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('cassReport').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cassReportPageUrlPattern);

        cassReport = undefined;
      });
    });
  });

  describe('new CassReport page', () => {
    beforeEach(() => {
      cy.visit(cassReportPageUrl);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('CassReport');
    });

    it('should create an instance of CassReport', () => {
      cy.get(`[data-cy="id"]`).type('00000000-0000-4000-8000-000000000001');
      cy.get(`[data-cy="id"]`).should('have.value', '00000000-0000-4000-8000-000000000001');

      cy.get(`[data-cy="fileName"]`).type('large');
      cy.get(`[data-cy="fileName"]`).should('have.value', 'large');

      cy.get(`[data-cy="fileExtension"]`).type('old woeful');
      cy.get(`[data-cy="fileExtension"]`).should('have.value', 'old woeful');
      cy.setFieldImageAsBytesOfEntity('file', 'integration-test.png', 'image/png');

      cy.get(`[data-cy="approved"]`).should('not.be.checked');
      cy.get(`[data-cy="approved"]`).click();
      cy.get(`[data-cy="approved"]`).should('be.checked');

      // since cypress clicks submit too fast before the blob fields are validated
      cy.wait(200); // eslint-disable-line cypress/no-unnecessary-waiting
      cy.get(`[data-cy="createDate-generate"]`).click({ force: true });

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        cassReport = response.body;
      });
      cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', cassReportPageUrlPattern);
    });

    it('should accept input on the createDate date-time widget sub-inputs', () => {
      cy.get(`[data-cy="createDate-hours"]`).invoke('val', '10').trigger('input', { force: true });
      cy.get(`[data-cy="createDate-hours"]`).should('have.value', '10');

      cy.get(`[data-cy="createDate-minutes"]`).invoke('val', '30').trigger('input', { force: true });
      cy.get(`[data-cy="createDate-minutes"]`).should('have.value', '30');

      cy.get(`[data-cy="createDate-ampm"]`).click({ force: true });
      cy.get('mat-option').contains('AM').click({ force: true });
      cy.get(`[data-cy="createDate-ampm"]`).should('contain', 'AM');
    });
  });

  it('should generate and reset a UUID via the form buttons', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem(cassReportPageUrl.substring(1));
    cy.get(entityCreateButtonSelector, { timeout: 30000 }).click();
    // Generate fills a fresh UUID via the component's generateUUID()/generateTimeUUID().
    cy.get(`[data-cy="id-generate"]`).click();
    cy.get(`[data-cy="id"]`)
      .invoke('val')
      .should('match', /^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$/i);
    // Reset restores the (empty) saved value, clearing the field.
    cy.get(`[data-cy="id-reset"]`).click();
    cy.get(`[data-cy="id"]`).should('have.value', '');
  });
});
