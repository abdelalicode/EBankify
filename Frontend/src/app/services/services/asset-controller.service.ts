/* tslint:disable */
/* eslint-disable */
/* Code generated by ng-openapi-gen DO NOT EDIT. */

import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { Asset } from '../models/asset';
import { AssetResponseDto } from '../models/asset-response-dto';
import { createAsset } from '../fn/asset-controller/create-asset';
import { CreateAsset$Params } from '../fn/asset-controller/create-asset';
import { findById2 } from '../fn/asset-controller/find-by-id-2';
import { FindById2$Params } from '../fn/asset-controller/find-by-id-2';
import { getAllAssets } from '../fn/asset-controller/get-all-assets';
import { GetAllAssets$Params } from '../fn/asset-controller/get-all-assets';
import { updateAsset } from '../fn/asset-controller/update-asset';
import { UpdateAsset$Params } from '../fn/asset-controller/update-asset';

@Injectable({ providedIn: 'root' })
export class AssetControllerService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `findById2()` */
  static readonly FindById2Path = '/assets/{id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `findById2()` instead.
   *
   * This method doesn't expect any request body.
   */
  findById2$Response(params: FindById2$Params, context?: HttpContext): Observable<StrictHttpResponse<Asset>> {
    return findById2(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `findById2$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  findById2(params: FindById2$Params, context?: HttpContext): Observable<Asset> {
    return this.findById2$Response(params, context).pipe(
      map((r: StrictHttpResponse<Asset>): Asset => r.body)
    );
  }

  /** Path part for operation `updateAsset()` */
  static readonly UpdateAssetPath = '/assets/{id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `updateAsset()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  updateAsset$Response(params: UpdateAsset$Params, context?: HttpContext): Observable<StrictHttpResponse<Asset>> {
    return updateAsset(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `updateAsset$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  updateAsset(params: UpdateAsset$Params, context?: HttpContext): Observable<Asset> {
    return this.updateAsset$Response(params, context).pipe(
      map((r: StrictHttpResponse<Asset>): Asset => r.body)
    );
  }

  /** Path part for operation `getAllAssets()` */
  static readonly GetAllAssetsPath = '/assets';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getAllAssets()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllAssets$Response(params?: GetAllAssets$Params, context?: HttpContext): Observable<StrictHttpResponse<Array<Asset>>> {
    return getAllAssets(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getAllAssets$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllAssets(params?: GetAllAssets$Params, context?: HttpContext): Observable<Array<Asset>> {
    return this.getAllAssets$Response(params, context).pipe(
      map((r: StrictHttpResponse<Array<Asset>>): Array<Asset> => r.body)
    );
  }

  /** Path part for operation `createAsset()` */
  static readonly CreateAssetPath = '/assets';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `createAsset()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createAsset$Response(params: CreateAsset$Params, context?: HttpContext): Observable<StrictHttpResponse<AssetResponseDto>> {
    return createAsset(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `createAsset$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createAsset(params: CreateAsset$Params, context?: HttpContext): Observable<AssetResponseDto> {
    return this.createAsset$Response(params, context).pipe(
      map((r: StrictHttpResponse<AssetResponseDto>): AssetResponseDto => r.body)
    );
  }

}
