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

describe('PsqlProduct e2e test', () => {
  const psqlProductPageUrl = '/psqlstore/psql-product';
  const psqlProductPageUrlPattern = new RegExp('/psqlstore/psql-product(\\?.*)?$');
  let username: string;
  let password: string;
  const psqlProductSample = { title: 'because', price: 22963.73 };

  let psqlProduct;

  before(() => {
    cy.credentials().then(credentials => {
      ({ username, password } = credentials);
    });
  });

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/services/psqlstore/api/psql-products+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/services/psqlstore/api/psql-products').as('postEntityRequest');
    cy.intercept('DELETE', '/services/psqlstore/api/psql-products/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (psqlProduct) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/psqlstore/api/psql-products/${psqlProduct.id}`,
      }).then(() => {
        psqlProduct = undefined;
      });
    }
  });

  it('PsqlProducts menu should load PsqlProducts page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('psqlstore/psql-product');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('PsqlProduct').should('exist');
    cy.url().should('match', psqlProductPageUrlPattern);
  });

  describe('PsqlProduct page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(psqlProductPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create PsqlProduct page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/psqlstore/psql-product/new$'));
        cy.getEntityCreateUpdateHeading('PsqlProduct');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', psqlProductPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/psqlstore/api/psql-products',
          body: psqlProductSample,
        }).then(({ body }) => {
          psqlProduct = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/services/psqlstore/api/psql-products+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/services/psqlstore/api/psql-products?page=0&size=20>; rel="last",<http://localhost/services/psqlstore/api/psql-products?page=0&size=20>; rel="first"',
              },
              body: [psqlProduct],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(psqlProductPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details PsqlProduct page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('psqlProduct');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', psqlProductPageUrlPattern);
      });

      it('edit button click should load edit PsqlProduct page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('PsqlProduct');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', psqlProductPageUrlPattern);
      });

      it('edit button click should load edit PsqlProduct page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('PsqlProduct');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', psqlProductPageUrlPattern);
      });

      it('last delete button click should delete instance of PsqlProduct', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('psqlProduct').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', psqlProductPageUrlPattern);

        psqlProduct = undefined;
      });
    });
  });

  describe('new PsqlProduct page', () => {
    beforeEach(() => {
      cy.visit(psqlProductPageUrl);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('PsqlProduct');
    });

    it('should create an instance of PsqlProduct', () => {
      cy.get(`[data-cy="title"]`).type('indeed joyously');
      cy.get(`[data-cy="title"]`).should('have.value', 'indeed joyously');

      cy.get(`[data-cy="price"]`).type('1898.82');
      cy.get(`[data-cy="price"]`).should('have.value', '1898.82');

      cy.setFieldImageAsBytesOfEntity('image', 'integration-test.png', 'image/png');

      // since cypress clicks submit too fast before the blob fields are validated
      cy.wait(200); // eslint-disable-line cypress/no-unnecessary-waiting
      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        psqlProduct = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', psqlProductPageUrlPattern);
    });
  });
});
