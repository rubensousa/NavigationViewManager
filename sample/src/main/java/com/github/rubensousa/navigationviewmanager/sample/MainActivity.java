/*
 * Copyright 2016 Rúben Sousa
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.rubensousa.navigationviewmanager.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.github.rubensousa.navigationmanager.NavigationManager;

public class MainActivity extends AppCompatActivity implements NavigationManager.NavigationListener {

    private NavigationManager mNavigationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        mNavigationManager = new NavigationViewManager(getSupportFragmentManager(),
                navigationView, drawer, R.id.frameLayout);

        mNavigationManager.setNavigationListener(this);
        mNavigationManager.init(savedInstanceState, getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mNavigationManager.navigateWithIntent(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mNavigationManager.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        if (!mNavigationManager.closeDrawer()) {
            super.onBackPressed();
        }
    }

    public void setupToolbar(Toolbar toolbar) {
        toolbar.setNavigationIcon(R.drawable.ic_menu_24dp);
        toolbar.setTitle(mNavigationManager.getCurrentTitle());
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNavigationManager.openDrawer();
            }
        });
    }

    public void navigateWithIntent(Intent intent) {
        mNavigationManager.navigateWithIntent(intent);
    }

    public void navigate(@IdRes int menuId) {
        mNavigationManager.navigate(menuId);
    }

    @Override
    public void onItemSelected(MenuItem item) {
    }

    @Override
    public void onSectionChange(Fragment currentFragment) {
        // Only called when the user changes sections
        // We can use this to fade the current fragment's view, for example
        View view = currentFragment.getView();
        if (view != null) {
            View contentLayout = view.findViewById(R.id.contentLayout);
            if (contentLayout != null) {
                contentLayout.animate().setDuration(200).alpha(0);
            }
        }
    }
}
