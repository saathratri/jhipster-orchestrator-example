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

describe('CassBlog e2e test', () => {
  const cassBlogPageUrl = '/cassandrablog/cass-blog';
  const cassBlogPageUrlPattern = new RegExp('/cassandrablog/cass-blog(\\?.*)?$');
  let username: string;
  let password: string;
  const cassBlogSample = {
    compositeId: { category: 'sample-category-1', blogId: '00000000-0000-4000-8000-000000000001' },
    handle: 'as inasmuch fuel',
    content: 'circulate close',
  };

  let cassBlog;

  before(() => {
    cy.credentials().then(credentials => {
      ({ username, password } = credentials);
    });
  });

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', /^\/services\/cassandrablog\/api\/cass-blogs\b/).as('entitiesRequest');
    cy.intercept('POST', '/services/cassandrablog/api/cass-blogs').as('postEntityRequest');
    cy.intercept('DELETE', '/services/cassandrablog/api/cass-blogs/*/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (cassBlog) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/cassandrablog/api/cass-blogs/${cassBlog.compositeId.category}/${cassBlog.compositeId.blogId}`,
      }).then(() => {
        cassBlog = undefined;
      });
    }
  });

  it('CassBlogs menu should load CassBlogs page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('cassandrablog/cass-blog');
    cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('CassBlog').should('exist');
    cy.url().should('match', cassBlogPageUrlPattern);
  });

  describe('CassBlog page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(cassBlogPageUrl);
        cy.wait('@entitiesRequest', { timeout: 30000 });
      });

      it('should load create CassBlog page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/cassandrablog/cass-blog/new$'));
        cy.getEntityCreateUpdateHeading('CassBlog');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cassBlogPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/cassandrablog/api/cass-blogs',
          body: cassBlogSample,
        }).then(({ body }) => {
          cassBlog = body;

          cy.intercept(
            {
              method: 'GET',
              url: /^\/services\/cassandrablog\/api\/cass-blogs\b/,
              times: 1,
            },
            {
              statusCode: 200,
              body: [cassBlog],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(cassBlogPageUrl);

        cy.wait('@entitiesRequestInternal', { timeout: 30000 });
      });

      it('detail button click should load details CassBlog page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('cassBlog');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cassBlogPageUrlPattern);
      });

      it('edit button click should load edit CassBlog page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CassBlog');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cassBlogPageUrlPattern);
      });

      it('edit button click should load edit CassBlog page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CassBlog');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cassBlogPageUrlPattern);
      });

      it('last delete button click should delete instance of CassBlog', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('cassBlog').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cassBlogPageUrlPattern);

        cassBlog = undefined;
      });
    });
  });

  describe('new CassBlog page', () => {
    beforeEach(() => {
      cy.visit(cassBlogPageUrl);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('CassBlog');
    });

    it('should create an instance of CassBlog', () => {
      cy.get(`[data-cy="category"]`).type('3aa84191-c6dd-4ace-8ebd-dded7dfed93e');
      cy.get(`[data-cy="category"]`).should('have.value', '3aa84191-c6dd-4ace-8ebd-dded7dfed93e');

      cy.get(`[data-cy="blogId"]`).type('bf29f8e3-3109-4e78-b022-04d78f625ee3');
      cy.get(`[data-cy="blogId"]`).invoke('val').should('match', new RegExp('bf29f8e3-3109-4e78-b022-04d78f625ee3'));

      cy.get(`[data-cy="handle"]`).type('smug reporter where');
      cy.get(`[data-cy="handle"]`).should('have.value', 'smug reporter where');

      cy.get(`[data-cy="content"]`).type('pessimistic');
      cy.get(`[data-cy="content"]`).should('have.value', 'pessimistic');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        cassBlog = response.body;
      });
      cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', cassBlogPageUrlPattern);
    });
  });

  it('should toggle the Cassandra search form', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem(cassBlogPageUrl.substring(1));
    cy.get('[data-cy="searchFormToggle"]', { timeout: 30000 }).click();
    cy.get('[data-cy="searchButton"]').should('be.visible');
  });

  it('should generate and reset a UUID via the form buttons', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem(cassBlogPageUrl.substring(1));
    cy.get(entityCreateButtonSelector, { timeout: 30000 }).click();
    // Generate fills a fresh UUID via the component's generateUUID()/generateTimeUUID().
    cy.get(`[data-cy="blogId-generate"]`).click();
    cy.get(`[data-cy="blogId"]`)
      .invoke('val')
      .should('match', /^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$/i);
    // Reset restores the (empty) saved value, clearing the field.
    cy.get(`[data-cy="blogId-reset"]`).click();
    cy.get(`[data-cy="blogId"]`).should('have.value', '');
  });
});
