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

describe('PsqlPost e2e test', () => {
  const psqlPostPageUrl = '/psqlblog/psql-post';
  const psqlPostPageUrlPattern = new RegExp('/psqlblog/psql-post(\\?.*)?$');
  let username: string;
  let password: string;
  const psqlPostSample = {
    title: 'incidentally meh yearly',
    content: 'Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci50eHQ=',
    date: '2026-05-30T08:14:27.520Z',
  };

  let psqlPost;

  before(() => {
    cy.credentials().then(credentials => {
      ({ username, password } = credentials);
    });
  });

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/services/psqlblog/api/psql-posts+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/services/psqlblog/api/psql-posts').as('postEntityRequest');
    cy.intercept('DELETE', '/services/psqlblog/api/psql-posts/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (psqlPost) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/psqlblog/api/psql-posts/${psqlPost.id}`,
      }).then(() => {
        psqlPost = undefined;
      });
    }
  });

  it('PsqlPosts menu should load PsqlPosts page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('psqlblog/psql-post');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('PsqlPost').should('exist');
    cy.url().should('match', psqlPostPageUrlPattern);
  });

  describe('PsqlPost page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(psqlPostPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create PsqlPost page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/psqlblog/psql-post/new$'));
        cy.getEntityCreateUpdateHeading('PsqlPost');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', psqlPostPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/psqlblog/api/psql-posts',
          body: psqlPostSample,
        }).then(({ body }) => {
          psqlPost = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/services/psqlblog/api/psql-posts+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/services/psqlblog/api/psql-posts?page=0&size=20>; rel="last",<http://localhost/services/psqlblog/api/psql-posts?page=0&size=20>; rel="first"',
              },
              body: [psqlPost],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(psqlPostPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details PsqlPost page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('psqlPost');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', psqlPostPageUrlPattern);
      });

      it('edit button click should load edit PsqlPost page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('PsqlPost');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', psqlPostPageUrlPattern);
      });

      it('edit button click should load edit PsqlPost page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('PsqlPost');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', psqlPostPageUrlPattern);
      });

      it('last delete button click should delete instance of PsqlPost', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('psqlPost').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', psqlPostPageUrlPattern);

        psqlPost = undefined;
      });
    });
  });

  describe('new PsqlPost page', () => {
    beforeEach(() => {
      cy.visit(psqlPostPageUrl);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('PsqlPost');
    });

    it('should create an instance of PsqlPost', () => {
      cy.get(`[data-cy="title"]`).type('divert dress');
      cy.get(`[data-cy="title"]`).should('have.value', 'divert dress');

      cy.get(`[data-cy="content"]`).type('../fake-data/blob/hipster.txt');
      cy.get(`[data-cy="content"]`).invoke('val').should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="date"]`).type('2026-05-30T11:03');
      cy.get(`[data-cy="date"]`).blur();
      cy.get(`[data-cy="date"]`).should('have.value', '2026-05-30T11:03');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        psqlPost = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', psqlPostPageUrlPattern);
    });
  });
});
