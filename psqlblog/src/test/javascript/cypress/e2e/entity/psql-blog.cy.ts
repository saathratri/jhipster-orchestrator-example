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

describe('PsqlBlog e2e test', () => {
  const psqlBlogPageUrl = '/psqlblog/psql-blog';
  const psqlBlogPageUrlPattern = new RegExp('/psqlblog/psql-blog(\\?.*)?$');
  let username: string;
  let password: string;
  const psqlBlogSample = { name: 'helpless', handle: 'yuck boo' };

  let psqlBlog;

  before(() => {
    cy.credentials().then(credentials => {
      ({ username, password } = credentials);
    });
  });

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/services/psqlblog/api/psql-blogs+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/services/psqlblog/api/psql-blogs').as('postEntityRequest');
    cy.intercept('DELETE', '/services/psqlblog/api/psql-blogs/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (psqlBlog) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/psqlblog/api/psql-blogs/${psqlBlog.id}`,
      }).then(() => {
        psqlBlog = undefined;
      });
    }
  });

  it('PsqlBlogs menu should load PsqlBlogs page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('psqlblog/psql-blog');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('PsqlBlog').should('exist');
    cy.url().should('match', psqlBlogPageUrlPattern);
  });

  describe('PsqlBlog page', () => {
    it('should have translated page title', () => {
      cy.visit(psqlBlogPageUrl);
      cy.getEntityHeading('PsqlBlog').should('not.contain', 'psqlblogApp.psqlblogPsqlBlog.home.title');
    });

    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(psqlBlogPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create PsqlBlog page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/psqlblog/psql-blog/new$'));
        cy.getEntityCreateUpdateHeading('PsqlBlog');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', psqlBlogPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/psqlblog/api/psql-blogs',
          body: psqlBlogSample,
        }).then(({ body }) => {
          psqlBlog = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/services/psqlblog/api/psql-blogs+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [psqlBlog],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(psqlBlogPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details PsqlBlog page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('psqlBlog');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', psqlBlogPageUrlPattern);
      });

      it('edit button click should load edit PsqlBlog page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('PsqlBlog');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', psqlBlogPageUrlPattern);
      });

      it('edit button click should load edit PsqlBlog page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('PsqlBlog');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', psqlBlogPageUrlPattern);
      });

      it('last delete button click should delete instance of PsqlBlog', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('psqlBlog').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', psqlBlogPageUrlPattern);

        psqlBlog = undefined;
      });
    });
  });

  describe('new PsqlBlog page', () => {
    beforeEach(() => {
      cy.visit(psqlBlogPageUrl);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('PsqlBlog');
    });

    it('should create an instance of PsqlBlog', () => {
      cy.get(`[data-cy="name"]`).type('next appliance');
      cy.get(`[data-cy="name"]`).should('have.value', 'next appliance');

      cy.get(`[data-cy="handle"]`).type('waist boring');
      cy.get(`[data-cy="handle"]`).should('have.value', 'waist boring');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        psqlBlog = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', psqlBlogPageUrlPattern);
    });
  });
});
