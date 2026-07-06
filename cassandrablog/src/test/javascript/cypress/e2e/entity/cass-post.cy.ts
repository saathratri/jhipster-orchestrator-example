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

describe('CassPost e2e test', () => {
  const cassPostPageUrl = '/cassandrablog/cass-post';
  const cassPostPageUrlPattern = new RegExp('/cassandrablog/cass-post(\\?.*)?$');
  let username: string;
  let password: string;
  const cassPostSample = {
    compositeId: { createdDate: 1001, addedDateTime: 1001, postId: '00000000-0000-4000-8000-000000000001' },
    title: 'after youthfully',
    content: 'assist',
  };

  let cassPost;

  before(() => {
    cy.credentials().then(credentials => {
      ({ username, password } = credentials);
    });
  });

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', /^\/services\/cassandrablog\/api\/cass-posts\b/).as('entitiesRequest');
    cy.intercept('POST', '/services/cassandrablog/api/cass-posts').as('postEntityRequest');
    cy.intercept('DELETE', '/services/cassandrablog/api/cass-posts/*/*/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (cassPost) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/cassandrablog/api/cass-posts/${cassPost.compositeId.createdDate}/${cassPost.compositeId.addedDateTime}/${cassPost.compositeId.postId}`,
      }).then(() => {
        cassPost = undefined;
      });
    }
  });

  it('CassPosts menu should load CassPosts page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('cassandrablog/cass-post');
    cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('CassPost').should('exist');
    cy.url().should('match', cassPostPageUrlPattern);
  });

  describe('CassPost page', () => {
    it('should have translated page title', () => {
      cy.visit(cassPostPageUrl);
      cy.getEntityHeading('CassPost').should('not.contain', 'cassandrablogApp.cassandrablogCassPost.home.title');
    });

    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(cassPostPageUrl);
        cy.wait('@entitiesRequest', { timeout: 30000 });
      });

      it('should load create CassPost page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/cassandrablog/cass-post/new$'));
        cy.getEntityCreateUpdateHeading('CassPost');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cassPostPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/cassandrablog/api/cass-posts',
          body: cassPostSample,
        }).then(({ body }) => {
          cassPost = body;

          cy.intercept(
            {
              method: 'GET',
              url: /^\/services\/cassandrablog\/api\/cass-posts\b/,
              times: 1,
            },
            {
              statusCode: 200,
              body: [cassPost],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(cassPostPageUrl);

        cy.wait('@entitiesRequestInternal', { timeout: 30000 });
      });

      it('detail button click should load details CassPost page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('cassPost');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cassPostPageUrlPattern);
      });

      it('edit button click should load edit CassPost page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CassPost');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cassPostPageUrlPattern);
      });

      it('edit button click should load edit CassPost page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CassPost');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cassPostPageUrlPattern);
      });

      it('last delete button click should delete instance of CassPost', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('cassPost').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cassPostPageUrlPattern);

        cassPost = undefined;
      });
    });
  });

  describe('new CassPost page', () => {
    beforeEach(() => {
      cy.visit(cassPostPageUrl);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('CassPost');
    });

    it('should create an instance of CassPost', () => {
      cy.get(`[data-cy="createdDate"]`).type('1001');
      cy.get(`[data-cy="createdDate"]`).should('have.value', '1001');
      cy.get(`[data-cy="postId"]`).type('7626ab01-4e25-4930-8389-0017b2bfccd9');
      cy.get(`[data-cy="postId"]`).invoke('val').should('match', new RegExp('7626ab01-4e25-4930-8389-0017b2bfccd9'));

      cy.get(`[data-cy="title"]`).type('whether soft brr');
      cy.get(`[data-cy="title"]`).should('have.value', 'whether soft brr');

      cy.get(`[data-cy="content"]`).type('wherever');
      cy.get(`[data-cy="content"]`).should('have.value', 'wherever');
      cy.get(`[data-cy="sentDate"]`).type('4327');
      cy.get(`[data-cy="sentDate"]`).should('have.value', '4327');

      cy.get(`[data-cy="addedDateTime-generate"]`).click({ force: true });
      cy.get(`[data-cy="publishedDateTime-generate"]`).click({ force: true });

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        cassPost = response.body;
      });
      cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', cassPostPageUrlPattern);
    });

    it('should accept input on the addedDateTime date-time widget sub-inputs', () => {
      cy.get(`[data-cy="addedDateTime-hours"]`).invoke('val', '10').trigger('input', { force: true });
      cy.get(`[data-cy="addedDateTime-hours"]`).should('have.value', '10');

      cy.get(`[data-cy="addedDateTime-minutes"]`).invoke('val', '30').trigger('input', { force: true });
      cy.get(`[data-cy="addedDateTime-minutes"]`).should('have.value', '30');

      cy.get(`[data-cy="addedDateTime-ampm"]`).click({ force: true });
      cy.get('mat-option').contains('AM').click({ force: true });
      cy.get(`[data-cy="addedDateTime-ampm"]`).should('contain', 'AM');
    });

    it('should accept input on the publishedDateTime date-time widget sub-inputs', () => {
      cy.get(`[data-cy="publishedDateTime-hours"]`).invoke('val', '10').trigger('input', { force: true });
      cy.get(`[data-cy="publishedDateTime-hours"]`).should('have.value', '10');

      cy.get(`[data-cy="publishedDateTime-minutes"]`).invoke('val', '30').trigger('input', { force: true });
      cy.get(`[data-cy="publishedDateTime-minutes"]`).should('have.value', '30');

      cy.get(`[data-cy="publishedDateTime-ampm"]`).click({ force: true });
      cy.get('mat-option').contains('AM').click({ force: true });
      cy.get(`[data-cy="publishedDateTime-ampm"]`).should('contain', 'AM');
    });
  });

  it('should toggle the Cassandra search form', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem(cassPostPageUrl.substring(1));
    cy.get('[data-cy="searchFormToggle"]', { timeout: 30000 }).click();
    cy.get('[data-cy="searchButton"]').should('be.visible');
  });

  it('should generate and reset a UUID via the form buttons', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem(cassPostPageUrl.substring(1));
    cy.get(entityCreateButtonSelector, { timeout: 30000 }).click();
    // Generate fills a fresh UUID via the component's generateUUID()/generateTimeUUID().
    cy.get(`[data-cy="postId-generate"]`).click();
    cy.get(`[data-cy="postId"]`)
      .invoke('val')
      .should('match', /^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$/i);
    // Reset restores the (empty) saved value, clearing the field.
    cy.get(`[data-cy="postId-reset"]`).click();
    cy.get(`[data-cy="postId"]`).should('have.value', '');
  });
});
