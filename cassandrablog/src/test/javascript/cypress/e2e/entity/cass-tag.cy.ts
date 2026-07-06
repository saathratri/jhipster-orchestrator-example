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

describe('CassTag e2e test', () => {
  const cassTagPageUrl = '/cassandrablog/cass-tag';
  const cassTagPageUrlPattern = new RegExp('/cassandrablog/cass-tag(\\?.*)?$');
  let username: string;
  let password: string;
  const cassTagSample = { id: '00000000-0000-4000-8000-000000000001', name: 'yowza honorable given' };

  let cassTag;

  before(() => {
    cy.credentials().then(credentials => {
      ({ username, password } = credentials);
    });
  });

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', /^\/services\/cassandrablog\/api\/cass-tags\b/).as('entitiesRequest');
    cy.intercept('POST', '/services/cassandrablog/api/cass-tags').as('postEntityRequest');
    cy.intercept('DELETE', '/services/cassandrablog/api/cass-tags/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (cassTag) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/cassandrablog/api/cass-tags/${cassTag.id}`,
      }).then(() => {
        cassTag = undefined;
      });
    }
  });

  it('CassTags menu should load CassTags page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('cassandrablog/cass-tag');
    cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('CassTag').should('exist');
    cy.url().should('match', cassTagPageUrlPattern);
  });

  describe('CassTag page', () => {
    it('should have translated page title', () => {
      cy.visit(cassTagPageUrl);
      cy.getEntityHeading('CassTag').should('not.contain', 'cassandrablogApp.cassandrablogCassTag.home.title');
    });

    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(cassTagPageUrl);
        cy.wait('@entitiesRequest', { timeout: 30000 });
      });

      it('should load create CassTag page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/cassandrablog/cass-tag/new$'));
        cy.getEntityCreateUpdateHeading('CassTag');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cassTagPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/cassandrablog/api/cass-tags',
          body: cassTagSample,
        }).then(({ body }) => {
          cassTag = body;

          cy.intercept(
            {
              method: 'GET',
              url: /^\/services\/cassandrablog\/api\/cass-tags\b/,
              times: 1,
            },
            {
              statusCode: 200,
              body: [cassTag],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(cassTagPageUrl);

        cy.wait('@entitiesRequestInternal', { timeout: 30000 });
      });

      it('detail button click should load details CassTag page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('cassTag');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cassTagPageUrlPattern);
      });

      it('edit button click should load edit CassTag page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CassTag');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cassTagPageUrlPattern);
      });

      it('edit button click should load edit CassTag page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CassTag');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cassTagPageUrlPattern);
      });

      it('last delete button click should delete instance of CassTag', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('cassTag').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cassTagPageUrlPattern);

        cassTag = undefined;
      });
    });
  });

  describe('new CassTag page', () => {
    beforeEach(() => {
      cy.visit(cassTagPageUrl);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('CassTag');
    });

    it('should create an instance of CassTag', () => {
      cy.get(`[data-cy="id"]`).type('00000000-0000-4000-8000-000000000001');
      cy.get(`[data-cy="id"]`).should('have.value', '00000000-0000-4000-8000-000000000001');

      cy.get(`[data-cy="name"]`).type('ouch easily uh-huh');
      cy.get(`[data-cy="name"]`).should('have.value', 'ouch easily uh-huh');

      cy.get(`[data-cy="description"]`).type('vain because bonfire');
      cy.get(`[data-cy="description"]`).should('have.value', 'vain because bonfire');

      // since cypress clicks submit too fast before the blob fields are validated
      cy.wait(200); // eslint-disable-line cypress/no-unnecessary-waiting
      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        cassTag = response.body;
      });
      cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', cassTagPageUrlPattern);
    });
  });

  it('should generate and reset a UUID via the form buttons', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem(cassTagPageUrl.substring(1));
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

  it('should run an AI semantic search', () => {
    cy.intercept('GET', /\/api\/cass-tags\/ai-search/).as('aiSearchRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('cassandrablog/cass-tag');
    cy.wait('@entitiesRequest', { timeout: 30000 });
    cy.get('#aiSearchQuery').type('semantic query');
    cy.get('form[name="aiSearchForm"] button.btn-warning').click();
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
      url: '/services/cassandrablog/api/cass-tags',
      body: { ...cassTagSample, name: createdText },
    }).then(({ body }) => {
      cassTag = body;
      // Embedding created on insert: the vector column is populated (exposed via the DTO).
      cy.authenticatedRequest({ method: 'GET', url: `/services/cassandrablog/api/cass-tags/${cassTag.id}` }).then(({ body: created }) => {
        expect(created.nameEmbedding, 'embedding generated on create').to.exist;
        const embeddingBefore = JSON.stringify(created.nameEmbedding);
        // The ANN search finds the new row for its exact text.
        cy.visit('/');
        cy.clickOnEntityMenuItem('cassandrablog/cass-tag');
        cy.wait('@entitiesRequest', { timeout: 30000 });
        cy.intercept('GET', /\/api\/cass-tags\/ai-search/).as('aiSearchCreated');
        cy.get('#aiSearchQuery').clear().type(createdText);
        cy.get('form[name="aiSearchForm"] button.btn-warning').click();
        cy.wait('@aiSearchCreated', { timeout: 30000 }).then(({ response }) => {
          expect(response.statusCode).to.eq(200);
          expect(response.body.map(row => row.id)).to.include(cassTag.id);
        });
        // Update the source text; the stored vector must be REGENERATED (value changes).
        cy.authenticatedRequest({
          method: 'PUT',
          url: `/services/cassandrablog/api/cass-tags/${cassTag.id}`,
          body: { ...created, name: updatedText },
        });
        cy.authenticatedRequest({ method: 'GET', url: `/services/cassandrablog/api/cass-tags/${cassTag.id}` }).then(({ body: updated }) => {
          expect(updated.nameEmbedding, 'embedding regenerated on update').to.exist;
          expect(JSON.stringify(updated.nameEmbedding)).to.not.eq(embeddingBefore);
        });
      });
    });
  });
});
