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
    it('should have translated page title', () => {
      cy.visit(psqlTagPageUrl);
      cy.getEntityHeading('PsqlTag').should('not.contain', 'psqlblogApp.psqlblogPsqlTag.home.title');
    });

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

  it('should run an AI semantic search', () => {
    cy.intercept('GET', /\/api\/psql-tags\/ai-search/).as('aiSearchRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('psqlblog/psql-tag');
    cy.wait('@entitiesRequest', { timeout: 30000 });
    cy.get('[data-cy="aiSearchInput"]').type('semantic query');
    cy.get('[data-cy="aiSearchButton"]').click();
    cy.wait('@aiSearchRequest', { timeout: 30000 }).its('response.statusCode').should('eq', 200);
  });

  it('should generate embeddings on create and regenerate on update (fake embedding model)', function () {
    cy.env(['fakeEmbeddings']).then(({ fakeEmbeddings }) => {
      if (!fakeEmbeddings) this.skip();
    });
    const createdText = `cypress embed ${Date.now()}`;
    const updatedText = `cypress reembed ${Date.now()}`;
    cy.authenticatedRequest({
      method: 'POST',
      url: '/services/psqlblog/api/psql-tags',
      body: { ...psqlTagSample, name: createdText },
    }).then(({ body }) => {
      psqlTag = body;
      cy.visit('/');
      cy.clickOnEntityMenuItem('psqlblog/psql-tag');
      cy.wait('@entitiesRequest', { timeout: 30000 });
      // Embedding created on insert: AI search finds the new row by its exact text.
      cy.intercept('GET', /\/api\/psql-tags\/ai-search/).as('aiSearchCreated');
      cy.get('[data-cy="aiSearchInput"]').clear().type(createdText);
      cy.get('[data-cy="aiSearchButton"]').click();
      cy.wait('@aiSearchCreated', { timeout: 30000 }).then(({ response }) => {
        expect(response.statusCode).to.eq(200);
        expect(response.body.map(row => row.id)).to.include(psqlTag.id);
      });
      // Update the source text through the API; the stored vector must be regenerated.
      cy.authenticatedRequest({
        method: 'PUT',
        url: `/services/psqlblog/api/psql-tags/${psqlTag.id}`,
        body: { ...psqlTag, name: updatedText },
      });
      cy.intercept('GET', /\/api\/psql-tags\/ai-search/).as('aiSearchUpdated');
      cy.get('[data-cy="aiSearchInput"]').clear().type(updatedText);
      cy.get('[data-cy="aiSearchButton"]').click();
      cy.wait('@aiSearchUpdated', { timeout: 30000 }).then(({ response }) => {
        expect(response.statusCode).to.eq(200);
        expect(response.body.map(row => row.id)).to.include(psqlTag.id);
      });
      // The OLD text must no longer match: proves the vector was replaced, not kept.
      cy.intercept('GET', /\/api\/psql-tags\/ai-search/).as('aiSearchStale');
      cy.get('[data-cy="aiSearchInput"]').clear().type(createdText);
      cy.get('[data-cy="aiSearchButton"]').click();
      cy.wait('@aiSearchStale', { timeout: 30000 }).then(({ response }) => {
        expect(response.statusCode).to.eq(200);
        expect(response.body.map(row => row.id)).to.not.include(psqlTag.id);
      });
    });
  });
});
