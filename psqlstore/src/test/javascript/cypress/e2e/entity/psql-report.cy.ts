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

describe('PsqlReport e2e test', () => {
  const psqlReportPageUrl = '/psqlstore/psql-report';
  const psqlReportPageUrlPattern = new RegExp('/psqlstore/psql-report(\\?.*)?$');
  let username: string;
  let password: string;
  const psqlReportSample = {
    fileName: 'after whisper',
    fileExtension: 'publicize',
    createDate: '2026-07-06T17:52:01.405Z',
    file: 'Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci5wbmc=',
    fileContentType: 'unknown',
  };

  let psqlReport;

  before(() => {
    cy.credentials().then(credentials => {
      ({ username, password } = credentials);
    });
  });

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/services/psqlstore/api/psql-reports+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/services/psqlstore/api/psql-reports').as('postEntityRequest');
    cy.intercept('DELETE', '/services/psqlstore/api/psql-reports/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (psqlReport) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/psqlstore/api/psql-reports/${psqlReport.id}`,
      }).then(() => {
        psqlReport = undefined;
      });
    }
  });

  it('PsqlReports menu should load PsqlReports page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('psqlstore/psql-report');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('PsqlReport').should('exist');
    cy.url().should('match', psqlReportPageUrlPattern);
  });

  describe('PsqlReport page', () => {
    it('should have translated page title', () => {
      cy.visit(psqlReportPageUrl);
      cy.getEntityHeading('PsqlReport').should('not.contain', 'psqlstoreApp.psqlstorePsqlReport.home.title');
    });

    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(psqlReportPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create PsqlReport page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/psqlstore/psql-report/new$'));
        cy.getEntityCreateUpdateHeading('PsqlReport');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', psqlReportPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/psqlstore/api/psql-reports',
          body: psqlReportSample,
        }).then(({ body }) => {
          psqlReport = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/services/psqlstore/api/psql-reports+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [psqlReport],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(psqlReportPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details PsqlReport page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('psqlReport');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', psqlReportPageUrlPattern);
      });

      it('edit button click should load edit PsqlReport page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('PsqlReport');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', psqlReportPageUrlPattern);
      });

      it('edit button click should load edit PsqlReport page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('PsqlReport');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', psqlReportPageUrlPattern);
      });

      it('last delete button click should delete instance of PsqlReport', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('psqlReport').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', psqlReportPageUrlPattern);

        psqlReport = undefined;
      });
    });
  });

  describe('new PsqlReport page', () => {
    beforeEach(() => {
      cy.visit(psqlReportPageUrl);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('PsqlReport');
    });

    it('should create an instance of PsqlReport', () => {
      cy.get(`[data-cy="fileName"]`).type('husky');
      cy.get(`[data-cy="fileName"]`).should('have.value', 'husky');

      cy.get(`[data-cy="fileExtension"]`).type('upside-dow');
      cy.get(`[data-cy="fileExtension"]`).should('have.value', 'upside-dow');

      cy.get(`[data-cy="createDate"]`).type('2026-07-06T17:52');
      cy.get(`[data-cy="createDate"]`).blur();
      cy.get(`[data-cy="createDate"]`).should('have.value', '2026-07-06T17:52');

      cy.setFieldImageAsBytesOfEntity('file', 'integration-test.png', 'image/png');

      cy.get(`[data-cy="approved"]`).should('not.be.checked');
      cy.get(`[data-cy="approved"]`).click();
      cy.get(`[data-cy="approved"]`).should('be.checked');

      // since cypress clicks submit too fast before the blob fields are validated
      cy.wait(200); // eslint-disable-line cypress/no-unnecessary-waiting
      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        psqlReport = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', psqlReportPageUrlPattern);
    });
  });
});
