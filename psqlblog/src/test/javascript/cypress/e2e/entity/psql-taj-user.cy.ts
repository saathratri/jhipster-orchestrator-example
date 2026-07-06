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

describe('PsqlTajUser e2e test', () => {
  const psqlTajUserPageUrl = '/psqlblog/psql-taj-user';
  const psqlTajUserPageUrlPattern = new RegExp('/psqlblog/psql-taj-user(\\?.*)?$');
  let username: string;
  let password: string;
  const psqlTajUserSample = { login: 'manner overreact unblinking' };

  let psqlTajUser;

  before(() => {
    cy.credentials().then(credentials => {
      ({ username, password } = credentials);
    });
  });

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/services/psqlblog/api/psql-taj-users+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/services/psqlblog/api/psql-taj-users').as('postEntityRequest');
    cy.intercept('DELETE', '/services/psqlblog/api/psql-taj-users/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (psqlTajUser) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/psqlblog/api/psql-taj-users/${psqlTajUser.id}`,
      }).then(() => {
        psqlTajUser = undefined;
      });
    }
  });

  it('PsqlTajUsers menu should load PsqlTajUsers page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('psqlblog/psql-taj-user');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('PsqlTajUser').should('exist');
    cy.url().should('match', psqlTajUserPageUrlPattern);
  });

  describe('PsqlTajUser page', () => {
    it('should have translated page title', () => {
      cy.visit(psqlTajUserPageUrl);
      cy.getEntityHeading('PsqlTajUser').should('not.contain', 'psqlblogApp.psqlblogPsqlTajUser.home.title');
    });

    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(psqlTajUserPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create PsqlTajUser page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/psqlblog/psql-taj-user/new$'));
        cy.getEntityCreateUpdateHeading('PsqlTajUser');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', psqlTajUserPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/psqlblog/api/psql-taj-users',
          body: psqlTajUserSample,
        }).then(({ body }) => {
          psqlTajUser = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/services/psqlblog/api/psql-taj-users+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [psqlTajUser],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(psqlTajUserPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details PsqlTajUser page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('psqlTajUser');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', psqlTajUserPageUrlPattern);
      });

      it('edit button click should load edit PsqlTajUser page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('PsqlTajUser');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', psqlTajUserPageUrlPattern);
      });

      it('edit button click should load edit PsqlTajUser page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('PsqlTajUser');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', psqlTajUserPageUrlPattern);
      });

      it('last delete button click should delete instance of PsqlTajUser', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('psqlTajUser').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', psqlTajUserPageUrlPattern);

        psqlTajUser = undefined;
      });
    });
  });

  describe('new PsqlTajUser page', () => {
    beforeEach(() => {
      cy.visit(psqlTajUserPageUrl);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('PsqlTajUser');
    });

    it('should create an instance of PsqlTajUser', () => {
      cy.get(`[data-cy="login"]`).type('that rarely');
      cy.get(`[data-cy="login"]`).should('have.value', 'that rarely');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        psqlTajUser = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', psqlTajUserPageUrlPattern);
    });
  });
});
