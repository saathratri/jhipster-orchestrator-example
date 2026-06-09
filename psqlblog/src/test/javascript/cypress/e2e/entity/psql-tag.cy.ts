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

describe('PsqlTag e2e test', () => {
  const psqlTagPageUrl = '/psqlblog/psql-tag';
  const psqlTagPageUrlPattern = new RegExp('/psqlblog/psql-tag(\\?.*)?$');
  let username: string;
  let password: string;
  const psqlTagSample = { name: 'trim mechanically prime' };

  let psqlTag;

  before(() => {
    cy.credentials().then(credentials => {
      ({ username, password } = credentials);
    });
  });

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/services/psqlblog/api/psql-tags+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/services/psqlblog/api/psql-tags').as('postEntityRequest');
    cy.intercept('DELETE', '/services/psqlblog/api/psql-tags/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (psqlTag) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/psqlblog/api/psql-tags/${psqlTag.id}`,
      }).then(() => {
        psqlTag = undefined;
      });
    }
  });

  it('PsqlTags menu should load PsqlTags page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('psqlblog/psql-tag');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('PsqlTag').should('exist');
    cy.url().should('match', psqlTagPageUrlPattern);
  });

  describe('PsqlTag page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(psqlTagPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create PsqlTag page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/psqlblog/psql-tag/new$'));
        cy.getEntityCreateUpdateHeading('PsqlTag');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', psqlTagPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/psqlblog/api/psql-tags',
          body: psqlTagSample,
        }).then(({ body }) => {
          psqlTag = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/services/psqlblog/api/psql-tags+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/services/psqlblog/api/psql-tags?page=0&size=20>; rel="last",<http://localhost/services/psqlblog/api/psql-tags?page=0&size=20>; rel="first"',
              },
              body: [psqlTag],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(psqlTagPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details PsqlTag page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('psqlTag');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', psqlTagPageUrlPattern);
      });

      it('edit button click should load edit PsqlTag page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('PsqlTag');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', psqlTagPageUrlPattern);
      });

      it('edit button click should load edit PsqlTag page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('PsqlTag');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', psqlTagPageUrlPattern);
      });

      it('last delete button click should delete instance of PsqlTag', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('psqlTag').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', psqlTagPageUrlPattern);

        psqlTag = undefined;
      });
    });
  });

  describe('new PsqlTag page', () => {
    beforeEach(() => {
      cy.visit(psqlTagPageUrl);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('PsqlTag');
    });

    it('should create an instance of PsqlTag', () => {
      cy.get(`[data-cy="name"]`).type('off');
      cy.get(`[data-cy="name"]`).should('have.value', 'off');

      cy.get(`[data-cy="description"]`).type('even word jellyfish');
      cy.get(`[data-cy="description"]`).should('have.value', 'even word jellyfish');

      // since cypress clicks submit too fast before the blob fields are validated
      cy.wait(200); // eslint-disable-line cypress/no-unnecessary-waiting
      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        psqlTag = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', psqlTagPageUrlPattern);
    });
  });
});
