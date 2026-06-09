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

describe('CassProduct e2e test', () => {
  const cassProductPageUrl = '/cassandrastore/cass-product';
  const cassProductPageUrlPattern = new RegExp('/cassandrastore/cass-product(\\?.*)?$');
  let username: string;
  let password: string;
  const cassProductSample = {
    id: '00000000-0000-4000-8000-000000000001',
    title: 'onto kindheartedly swerve',
    price: 1694.86,
    addedDate: 10073,
  };

  let cassProduct;

  before(() => {
    cy.credentials().then(credentials => {
      ({ username, password } = credentials);
    });
  });

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', /^\/services\/cassandrastore\/api\/cass-products\b/).as('entitiesRequest');
    cy.intercept('POST', '/services/cassandrastore/api/cass-products').as('postEntityRequest');
    cy.intercept('DELETE', '/services/cassandrastore/api/cass-products/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (cassProduct) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/cassandrastore/api/cass-products/${cassProduct.id}`,
      }).then(() => {
        cassProduct = undefined;
      });
    }
  });

  it('CassProducts menu should load CassProducts page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('cassandrastore/cass-product');
    cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('CassProduct').should('exist');
    cy.url().should('match', cassProductPageUrlPattern);
  });

  describe('CassProduct page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(cassProductPageUrl);
        cy.wait('@entitiesRequest', { timeout: 30000 });
      });

      it('should load create CassProduct page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/cassandrastore/cass-product/new$'));
        cy.getEntityCreateUpdateHeading('CassProduct');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cassProductPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/cassandrastore/api/cass-products',
          body: cassProductSample,
        }).then(({ body }) => {
          cassProduct = body;

          cy.intercept(
            {
              method: 'GET',
              url: /^\/services\/cassandrastore\/api\/cass-products\b/,
              times: 1,
            },
            {
              statusCode: 200,
              body: [cassProduct],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(cassProductPageUrl);

        cy.wait('@entitiesRequestInternal', { timeout: 30000 });
      });

      it('detail button click should load details CassProduct page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('cassProduct');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cassProductPageUrlPattern);
      });

      it('edit button click should load edit CassProduct page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CassProduct');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cassProductPageUrlPattern);
      });

      it('edit button click should load edit CassProduct page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CassProduct');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cassProductPageUrlPattern);
      });

      it('last delete button click should delete instance of CassProduct', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('cassProduct').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cassProductPageUrlPattern);

        cassProduct = undefined;
      });
    });
  });

  describe('new CassProduct page', () => {
    beforeEach(() => {
      cy.visit(cassProductPageUrl);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('CassProduct');
    });

    it('should create an instance of CassProduct', () => {
      cy.get(`[data-cy="id"]`).type('00000000-0000-4000-8000-000000000001');
      cy.get(`[data-cy="id"]`).should('have.value', '00000000-0000-4000-8000-000000000001');

      cy.get(`[data-cy="title"]`).type('sweet wilderness zowie');
      cy.get(`[data-cy="title"]`).should('have.value', 'sweet wilderness zowie');

      cy.get(`[data-cy="price"]`).type('505.69');
      cy.get(`[data-cy="price"]`).should('have.value', '505.69');

      cy.setFieldImageAsBytesOfEntity('image', 'integration-test.png', 'image/png');

      cy.get(`[data-cy="addedDate"]`).type('1636');
      cy.get(`[data-cy="addedDate"]`).should('have.value', '1636');

      // since cypress clicks submit too fast before the blob fields are validated
      cy.wait(200); // eslint-disable-line cypress/no-unnecessary-waiting
      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        cassProduct = response.body;
      });
      cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', cassProductPageUrlPattern);
    });
  });

  it('should generate and reset a UUID via the form buttons', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem(cassProductPageUrl.substring(1));
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
